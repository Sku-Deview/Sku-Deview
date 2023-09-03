package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.MemberSkill;
import kr.co.skudeview.domain.Skill;
import kr.co.skudeview.domain.Token;
import kr.co.skudeview.domain.enums.Gender;
import kr.co.skudeview.domain.enums.Role;
import kr.co.skudeview.infra.exception.DuplicatedException;
import kr.co.skudeview.infra.exception.InvalidRequestException;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.exception.WrongPasswordException;
import kr.co.skudeview.infra.jwt.JwtProvider;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.MemberSkillRepository;
import kr.co.skudeview.repository.SkillRepository;
import kr.co.skudeview.repository.TokenRepository;
import kr.co.skudeview.repository.search.MemberSearchRepository;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.request.TokenDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberSearchRepository memberSearchRepository;

    private final SkillRepository skillRepository;

    private final MemberSkillRepository memberSkillRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final TokenRepository tokenRepository;

    @Transactional
    @Override
    public void createMember(MemberRequestDto.CREATE create) {

        isTelephone(create.getTelephone());
        isEmail(create.getEmail());
        isNickname(create.getNickname());

        Member member = toEntity(create);

        final List<MemberSkill> memberSkills = getSkills(create.getSkillName(), member);

        member.changeMemberSkills(memberSkills);

        memberRepository.save(member);
    }

    @Override
    public MemberResponseDto.READ loginMember(MemberRequestDto.LOGIN login) {
        Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(login.getEmail());

        isMember(member);

        isPassword(login.getPassword(), member.get().getPassword());

        TokenDto tokenDto = TokenDto.builder()
                .accessToken(jwtProvider.createToken(member.get().getEmail(), String.valueOf(member.get().getRole())))
                .refreshToken(createRefreshToken(member.get()))
                .build();

        return toReadTokenDto(member.get(), tokenDto);
    }

    @Override
    public MemberResponseDto.READ getMemberDetail(Long memberId) {

        final Optional<Member> member = memberRepository.findMemberByIdAndDeleteAtFalse(memberId);

        isMember(member);

        return toReadDto(member.get());
    }

    @Override
    public List<MemberResponseDto.READ> getAllMembers() {
        List<Member> members = memberRepository.findAllByDeleteAtFalse();

        return members.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberResponseDto.READ> getSearchMembers(MemberRequestDto.CONDITION condition) {
        final List<Member> members = memberSearchRepository.find(condition);

        return members.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateMember(String email, MemberRequestDto.UPDATE update) {
        final Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        isMember(member);
        //isTelephone(update.getTelephone());
        isNickname(update.getNickname());

        final List<MemberSkill> memberSkills = updateSkillByMember(update.getSkillName(), member.get());

        member.get().updateMember(toUpdateDto(update));
        member.get().changeMemberSkills(memberSkills);

        memberRepository.save(member.get());
    }


    /*
    update를 요청할 때, 기존의 연관관계를 지정한 곳의 기존의 memberSkill을 모두 deleteAt = false로 변경
    새롭게 update를 요청한 memberSkill을 다시 새롭게 저장
     */
    private List<MemberSkill> updateSkillByMember(List<String> updateSkillName, Member member) {
        List<Skill> updateSkills = skillRepository.findAllByNameInAndDeleteAtFalse(updateSkillName);
        List<MemberSkill> originSkills = memberSkillRepository.findMemberSkillByMember_IdAndDeleteAtFalse(member.getId());

        originSkills.forEach(MemberSkill::changeDeleteAt);

        return updateSkills.stream()
                .map(skill -> MemberSkill.builder()
                        .member(member)
                        .skill(skill)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteMember(Long id) {
        final Optional<Member> member = memberRepository.findMemberByIdAndDeleteAtFalse(id);
        final List<MemberSkill> memberSkills = memberSkillRepository.findMemberSkillByMember_IdAndDeleteAtFalse(id);

        isMember(member);

        // 논리적 삭제
        member.get().changeDeleteAt();

        memberSkills.forEach(memberSkill -> {
            memberSkill.changeDeleteAt();
            memberSkillRepository.save(memberSkill);
        });

        memberRepository.save(member.get());
    }

    /*
    member를 생성할 때 입력한 skill을 string -> memberSkill로 변환
     */
    private List<MemberSkill> getSkills(List<String> skillNames, Member member) {

        List<Skill> skills = skillRepository.findAllByNameInAndDeleteAtFalse(skillNames);

        List<MemberSkill> memberSkills = skills.stream()
                .map(skill -> MemberSkill.builder()
                        .member(member)
                        .skill(skill)
                        .build())
                .collect(Collectors.toList());

        return memberSkills;
    }

    /*
    MemberSkill -> String으로 변환
     */
    @Override
    public Set<String> getSkillsNameByMember(Member member) {
        List<MemberSkill> memberSkills = memberSkillRepository.findMemberSkillByMember_IdAndDeleteAtFalse(member.getId());

        Set<String> skillNames = memberSkills
                .stream()
                .map(MemberSkill::getSkill)
                .map(Skill::getName)
                .collect(Collectors.toSet());

        return skillNames;
    }

    public String createRefreshToken(Member member) {
        Token token = tokenRepository.save(
                Token.builder()
                        .id(member.getId())
                        .refreshToken(UUID.randomUUID().toString())
                        .expiration(120)
                        .build()
        );
        return token.getRefreshToken();
    }

    public Token validRefreshToken(Member member, String refreshToken) {
        Optional<Token> token = tokenRepository.findById(member.getId());

        isToken(token);

        // redis 에 해당 유저의 토큰이 존재하는지 체크
        if (token.get().getRefreshToken() == null) {
            return null;
        } else {
            if (token.get().getExpiration() < 10) {
                token.get().setExpiration(1000);
                tokenRepository.save(token.get());
            }
            if (!token.get().getRefreshToken().equals(refreshToken)) {
                return null;
            } else {
                return token.get();
            }
        }
    }

    public TokenDto refreshAccessToken(TokenDto tokenDto) {
        String identity = jwtProvider.getIdentity(tokenDto.getAccessToken());
        log.info("identity = {}", identity);
        Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(identity);

        isMember(member);

        Token refreshToken = validRefreshToken(member.get(), tokenDto.getRefreshToken());

        isRefreshToken(refreshToken);

        return TokenDto.builder()
                .accessToken(jwtProvider.createToken(identity, String.valueOf(member.get().getRole())))
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isTelephone(String telephone) {
        if (memberRepository.existsMemberByTelephoneAndDeleteAtFalse(telephone)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_TELEPHONE_DUPLICATED);
        }
    }

    private void isNickname(String nickname) {
        if (memberRepository.existsMemberByNicknameAndDeleteAtFalse(nickname)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_NICKNAME_DUPLICATED);
        }
    }

    private void isEmail(String email) {
        if (memberRepository.existsMemberByEmailAndDeleteAtFalse(email)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_EMAIL_DUPLICATED);
        }
    }

    private void isPassword(String requestPassword, String getPassword) {
        if (!passwordEncoder.matches(requestPassword, getPassword)) {
            throw new WrongPasswordException(ResponseStatus.FAIL_MEMBER_PASSWORD_NOT_MATCHED);
        }
    }

    private void isToken(Optional<Token> token) {
        if (token.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_TOKEN_NOT_FOUND);
        }
    }

    private void isRefreshToken(Token refreshToken) {
        if (refreshToken == null) {
            throw new InvalidRequestException(ResponseStatus.FAIL_LOGIN_NOT_SUCCESS);
        }
    }

    private MemberResponseDto.READ toReadTokenDto(Member member, TokenDto tokenDto) {
        return MemberResponseDto.READ.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .address(member.getAddress())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getRole()))
                .skillName(getSkillsNameByMember(member))
                .accessToken(tokenDto.getAccessToken())
                .build();
    }

    private MemberResponseDto.READ toReadDto(Member member) {
        return MemberResponseDto.READ.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .address(member.getAddress())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getGender()))
                .skillName(getSkillsNameByMember(member))
                .build();
    }

    private MemberRequestDto.UPDATE toUpdateDto(MemberRequestDto.UPDATE update) {
        MemberRequestDto.UPDATE encoding = MemberRequestDto.UPDATE.builder()
                .password(passwordEncoder.encode(update.getPassword()))
                .address(update.getAddress())
                .role(update.getRole())
                .name(update.getName())
                .nickname(update.getNickname())
                .telephone(update.getTelephone())
                .birthDate(update.getBirthDate())
                .gender(update.getGender())
                .build();
        return encoding;
    }

    private Member toEntity(MemberRequestDto.CREATE create) {
        return Member.builder()
                .email(create.getEmail())
                .password(passwordEncoder.encode(create.getPassword()))
                .name(create.getName())
                .nickname(create.getNickname())
                .telephone(create.getTelephone())
                .address(create.getAddress())
                .birthDate(create.getBirthDate())
                .gender(Gender.valueOf(create.getGender()))
                .role(Role.ROLE_USER)
                .memberSkills(Collections.emptyList())
                .build();
    }

}
