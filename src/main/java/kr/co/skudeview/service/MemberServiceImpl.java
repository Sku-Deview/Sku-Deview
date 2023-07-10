package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.MemberSkill;
import kr.co.skudeview.domain.Skill;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.SkillRepository;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;
import kr.co.skudeview.service.mapper.MemberMapper;
import kr.co.skudeview.service.mapper.MemberSkillMapper;
import kr.co.skudeview.service.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final SkillRepository skillRepository;

    private final MemberMapper memberMapper;

    private final MemberSkillMapper memberSkillMapper;

    @Transactional
    @Override
    public void createMember(MemberRequestDto.CREATE create) throws Exception {

        isTelephone(create.getTelephone());
        isEmail(create.getEmail());
        isNickname(create.getEmail());

        Member member = memberMapper.toEntity(create, Collections.emptyList());

        final List<MemberSkill> memberSkills = getSkills(create.getSkillName(), member);

        member.changeMemberSkills(memberSkills);

        memberRepository.save(member);

    }

    private List<MemberSkill> getSkills(List<String> skillNames, Member member) {

        final List<Skill> skills = skillRepository.findAllByNameIn(skillNames);

        //연관 엔티티 반
        return skills.stream()
                .map(skill -> memberSkillMapper.toEntity(skill, member))
                .collect(Collectors.toList());
    }


    @Override
    public MemberResponseDto.READ getMemberByEmail(String email) throws Exception {
        final Optional<Member> member = memberRepository.findMemberByEmail(email);

        isMember(member);

        return memberMapper.toReadDto(member.get(), getSkillsNameByMember(member.get()));
    }

    @Override
    public List<MemberResponseDto.READ> getAllMembers() throws Exception {

        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto.READ> readList = new ArrayList<>();

        for (Member member : members) {
            List<String> skillsName = getSkillsNameByMember(member);
            MemberResponseDto.READ dto = memberMapper.toReadDto(member, skillsName);
            readList.add(dto);
        }

        return readList;
    }

    @Transactional
    @Override
    public void updateMember(MemberRequestDto.UPDATE update) throws Exception {
        final Optional<Member> member = memberRepository.findById(update.getMemberId());

        isMember(member);
        isTelephone(update.getTelephone());
        isNickname(update.getNickname());

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

    private List<String> getSkillsNameByMember(Member member) {
        return member
                .getMemberSkills()
                .stream()
                .map(MemberSkill::getSkill)
                .map(Skill::getName)
                .collect(Collectors.toList());
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
