package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.MemberSkill;
import kr.co.skudeview.domain.Skill;
import kr.co.skudeview.infra.exception.DuplicatedException;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.MemberSkillRepository;
import kr.co.skudeview.repository.SkillRepository;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final SkillRepository skillRepository;

    private final MemberSkillRepository memberSkillRepository;

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

    @Transactional
    @Override
    public void updateMember(MemberRequestDto.UPDATE update) {
        final Optional<Member> member = memberRepository.findMemberByIdAndDeleteAtFalse(update.getMemberId());

        isMember(member);
        isTelephone(update.getTelephone());
        isNickname(update.getNickname());

        final List<MemberSkill> memberSkills = updateSkillByMember(update.getSkillName(), member.get());

        member.get().updateMember(update);
        member.get().changeMemberSkills(memberSkills);

        memberRepository.save(member.get());
    }

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

        isMember(member);

        // 논리적 삭제
        member.get().changeDeleteAt();

        memberRepository.save(member.get());
    }

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

}
