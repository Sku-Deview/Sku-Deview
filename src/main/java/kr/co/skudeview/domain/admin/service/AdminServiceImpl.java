package kr.co.skudeview.domain.admin.service;

import kr.co.skudeview.domain.admin.service.AdminService;
import kr.co.skudeview.domain.member.dto.MemberRequestDto;
import kr.co.skudeview.domain.member.dto.MemberResponseDto;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.member.entity.MemberSkill;
import kr.co.skudeview.domain.member.repository.MemberRepository;
import kr.co.skudeview.domain.member.repository.MemberSkillRepository;
import kr.co.skudeview.domain.post.dto.PostResponseDto;
import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.domain.post.repository.PostRepository;
import kr.co.skudeview.domain.reply.dto.ReplyResponseDto;
import kr.co.skudeview.domain.reply.entity.Reply;
import kr.co.skudeview.domain.reply.repository.ReplyRepository;
import kr.co.skudeview.domain.report.entity.Report;
import kr.co.skudeview.domain.report.entity.dto.ReportResponseDto;
import kr.co.skudeview.domain.report.repository.ReportRepository;
import kr.co.skudeview.domain.skill.dto.SkillRequestDto;
import kr.co.skudeview.domain.skill.dto.SkillResponseDto;
import kr.co.skudeview.domain.skill.entity.Skill;
import kr.co.skudeview.domain.skill.repository.SkillRepository;
import kr.co.skudeview.global.common.PostCategory;
import kr.co.skudeview.global.common.Role;
import kr.co.skudeview.global.exception.DuplicatedException;
import kr.co.skudeview.global.exception.NotFoundException;
import kr.co.skudeview.global.exception.WrongPasswordException;
import kr.co.skudeview.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;

    private final MemberSkillRepository memberSkillRepository;

    private final SkillRepository skillRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final ReportRepository reportRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public List<MemberResponseDto.adminREAD> getAllMember() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long updateMember(Long memberId, MemberRequestDto.UPDATE update) {

        final Optional<Member> member = memberRepository.findMemberById(memberId);

        isMember(member);
        //isTelephone(update.getTelephone());
        //isNickname(update.getNickname());

        final List<MemberSkill> memberSkills = updateSkillByMember(update.getSkillName(), member.get());

        member.get().updateMember(toUpdateDto(update));
        member.get().changeMemberSkills(memberSkills);

        memberRepository.save(member.get());
        return member.get().getId();
    }

    @Override
    public MemberResponseDto.adminREAD getMemberDetail(Long memberId) {
        final Optional<Member> member = memberRepository.findMemberById(memberId);

        isMember(member);

        return toReadDto(member.get());
    }

    @Override
    public List<PostResponseDto.READ> getAllMemberPost(Long memberId) {
        List<Post> posts = postRepository.findPostsByMemberId(memberId);

        return posts.stream().map(this::toReadDto)
                .collect(Collectors.toList());

    }

    @Override
    public PostResponseDto.READ getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findPostById(postId);

        isPost(post);

        PostResponseDto.READ dto = toReadDto(post.get());

        return dto;
    }

    @Override
    public Long deletePost(Long postId) {
        Optional<Post> post = postRepository.findPostById(postId);

        isPost(post);

        post.get().changeDeleteAt();

        postRepository.save(post.get());

        return post.get().getId();
    }

    @Override
    public List<ReplyResponseDto.READ> getAllRepliesByMember(Long memberId) {
        List<Reply> list = replyRepository.findRepliesByMemberId(memberId);

        return list.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findReplyById(replyId);

        isReply(reply);

        reply.get().changeDeleteAt();

        replyRepository.save(reply.get());

        return reply.get().getId();
    }

    @Override
    public List<SkillResponseDto.READ> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();

        return skills.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateSkill(SkillRequestDto.UPDATE update) {
        final Optional<Skill> skill = skillRepository.findSkillById(update.getSkillId());

        isSkill(skill);

        isSkillName(update.getName());

        skill.get().updateSkill(update);

        skillRepository.save(skill.get());
    }

    @Override
    public List<ReportResponseDto.READ> getAllReports() {
        List<Report> reports = reportRepository.findAll();

        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponseDto.READ getReportDetail(Long reportId) {
        Optional<Report> report = reportRepository.findReportById(reportId);

        isReport(report);

        return toReadDto(report.get());
    }


    private MemberResponseDto.adminREAD toReadDto(Member member) {
        return MemberResponseDto.adminREAD.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .address(member.getAddress())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getRole()))
                .skillName(getSkillsNameByMember(member))
                .build();
    }

    private MemberRequestDto.UPDATE toUpdateDto(MemberRequestDto.UPDATE update) {
        MemberRequestDto.UPDATE encoding = MemberRequestDto.UPDATE.builder()
                .password(update.getPassword())
                .address(update.getAddress())
                .role(Role.of(update.getRole()).toString()) //이렇게 해도 되나..?
                .name(update.getName())
                .nickname(update.getNickname())
                .telephone(update.getTelephone())
                .birthDate(update.getBirthDate())
                .gender(update.getGender())
                .build();
        return encoding;
    }

    private PostResponseDto.READ toReadDto(Post post) {
        if (post.getFileAttached() == 0) {
            return PostResponseDto.READ.builder()
                    .postId(post.getId())
                    .memberEmail(post.getMember().getEmail())
                    .memberNickname(post.getMember().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .postCategory(post.getPostCategory())
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .fileAttached(post.getFileAttached())
                    .regDate(post.getRegDate())
                    .build();

        } else {
            return PostResponseDto.READ.builder()
                    .postId(post.getId())
                    .memberEmail(post.getMember().getEmail())
                    .memberNickname(post.getMember().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .postCategory(post.getPostCategory())
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .fileAttached(post.getFileAttached())
                    .originalFileName(post.getPostFileList().get(0).getOriginalFileName())
                    .storedFileName(post.getPostFileList().get(0).getStoredFileName())
                    .regDate(post.getRegDate())
                    .build();
        }

    }

    private ReplyResponseDto.READ toReadDto(Reply reply) {
        ReplyResponseDto.READ dto = ReplyResponseDto.READ.builder()
                .replyId(reply.getId())
                .memberNickname(reply.getMember().getNickname())
                .postId(reply.getPost().getId())
                .content(reply.getContent())
                .regDate(reply.getRegDate())
                .likeCount(reply.getLikeCount())
                .build();
        return dto;
    }
    private SkillResponseDto.READ toReadDto(Skill skill) {
        return SkillResponseDto.READ.builder()
                .skillId(skill.getId())
                .name(skill.getName())
                .build();
    }

    private ReportResponseDto.READ toReadDto(Report report) {
        return ReportResponseDto.READ.builder()
                .reportId(report.getId())
                .postId(report.getPost().getId())
                .memberEmail(report.getMember().getEmail())
                .memberName(report.getMember().getName())
                .reportCategory(String.valueOf(report.getReportCategory()))
                .postTitle(report.getPost().getTitle())
                .title(report.getTitle())
                .description(report.getDescription())
                .build();

    }
    public Set<String> getSkillsNameByMember(Member member) {
        List<MemberSkill> memberSkills = memberSkillRepository.findMemberSkillByMember_IdAndDeleteAtFalse(member.getId());

        Set<String> skillNames = memberSkills
                .stream()
                .map(MemberSkill::getSkill)
                .map(Skill::getName)
                .collect(Collectors.toSet());

        return skillNames;
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
    private void isReply(Optional<Reply> reply) {
        if (reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isPostCategory(String category) {
        PostCategory.of(category);
    }

    private void isSkill(Optional<Skill> skill) {
        if (skill.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_SKILL_NOT_FOUND);
        }
    }

    private void isSkillName(String name) {
        if (skillRepository.existsSkillByName(name)) {
            throw new DuplicatedException(ResponseStatus.FAIL_SKILL_NAME_DUPLICATED);
        }
    }
    private void isReport(Optional<Report> report) {
        if (report.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_NOT_FOUND);
        }
    }
}
