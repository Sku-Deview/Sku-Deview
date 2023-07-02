package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Member;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;
import kr.co.skudeview.service.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public void createMember(MemberRequestDto.CREATE create) throws Exception {
        final Member member = memberMapper.toEntity(create);

        isTelephone(member.getTelephone());
        isEmail(member.getEmail());
        isNickname(member.getNickname());

        memberRepository.save(member);
    }

    @Override
    public MemberResponseDto.READ getMemberByEmail(String email) throws Exception {
        final Optional<Member> member = memberRepository.findMemberByEmail(email);

        isMember(member);

        return memberMapper.toReadDto(member.get());
    }

    @Override
    public List<MemberResponseDto.READ> getAllMembers() throws Exception {
        final List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(memberMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateMember(MemberRequestDto.UPDATE update) throws Exception {
        final Optional<Member> member = memberRepository.findById(update.getMemberId());

        isMember(member);

        member.get().updateMember(update);

        memberRepository.save(member.get());
    }

    @Transactional
    @Override
    public void deleteMember(Long id) throws Exception {
        final Optional<Member> member = memberRepository.findById(id);

        isMember(member);

        /**
         * TODO
         *
         * 현재 BaseEntity에 deleteAt을 사용해주기에, 추후에 delete가 아닌, 해당 컬럼을 N -> Y 로 바꾸도록 수정이 필요
         */
        memberRepository.delete(member.get());
    }


    /**
     * validation + exception 처리를 위한 method
     *
     * TODO
     * Exception 추가해준것 모두 삭제 예정 -> Custom 한 Exception과 ResponseStatus추가 후 변경 예정
     */
    private void isMember(Optional<Member> member) throws Exception {
        if (member.isEmpty()) {
            throw new Exception("This Member is Not Exist");
        }
    }

    private void isTelephone(String telephone) throws Exception {
        if (memberRepository.existsMemberByTelephone(telephone)) {
            throw new Exception("This Telephone is Already Use");
        }
    }

    private void isNickname(String nickname) throws Exception {
        if (memberRepository.existsMemberByNickname(nickname)) {
            throw new Exception("This Nickname is Already Use");
        }
    }

    private void isEmail(String email) throws Exception {
        if (memberRepository.existsMemberByEmail(email)) {
            throw new Exception("This Email is Already Use");
        }
    }

}
