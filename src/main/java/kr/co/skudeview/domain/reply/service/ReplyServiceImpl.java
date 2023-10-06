package kr.co.skudeview.domain.reply.service;

import kr.co.skudeview.domain.member.entity.MemberLikePost;
import kr.co.skudeview.domain.member.entity.MemberLikeReply;
import kr.co.skudeview.domain.member.repository.MemberLikeReplyRepository;
import kr.co.skudeview.domain.reply.dto.ReplyRequestDto;
import kr.co.skudeview.domain.reply.dto.ReplyResponseDto;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.domain.reply.entity.Reply;
import kr.co.skudeview.global.exception.DuplicatedException;
import kr.co.skudeview.global.exception.NotFoundException;
import kr.co.skudeview.global.model.ResponseStatus;
import kr.co.skudeview.domain.member.repository.MemberRepository;
import kr.co.skudeview.domain.post.repository.PostRepository;
import kr.co.skudeview.domain.reply.repository.ReplyRepository;
import kr.co.skudeview.domain.reply.repository.ReplySearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final ReplySearchRepository replySearchRepository;

    private final MemberLikeReplyRepository memberLikeReplyRepository;

    @Override
    @Transactional
    public Long createReply(String email, Long postId, ReplyRequestDto.CREATE create) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        isMember(findMember);

        Optional<Post> findPost = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(findPost);

        Reply reply = toEntity(create, findMember.get(), findPost.get());

        replyRepository.save(reply);

        return reply.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDto.READ> getAllReplies(Long postId) {
        return replyRepository.findRepliesByPostIdAndDeleteAtFalse(postId).stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReplyResponseDto.READ> getSearchReplies(ReplyRequestDto.CONDITION condition) {
        return replySearchRepository.find(condition).stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long deleteReply(String email, Long postId, Long replyId) {
        Optional<Reply> reply = replyRepository.findReplyByIdAndDeleteAtFalse(replyId);

        isReply(reply);

        reply.get().changeDeleteAt();

        replyRepository.save(reply.get());

        return reply.get().getId();
    }

    @Override
    public List<ReplyResponseDto.READ> getRepliesByMemberNickname(String memberNickname) {
        Optional<Member> member = memberRepository.findMemberByNicknameAndDeleteAtFalse(memberNickname);

        isMember(member);

        return replySearchRepository.findRepliesByMemberId(member.get().getId()).stream().map(this::toReadDto).toList();
    }

    @Override
    public ReplyResponseDto.READ addRepliesLikeByLoginNickname(Long replyId, String loginNickname) {
        Optional<Member> loginMember = memberRepository.findMemberByNicknameAndDeleteAtFalse(loginNickname);
        Optional<Reply> reply = replyRepository.findReplyById(replyId);

        isReplyLikeDuplicated(replyId, loginMember.get().getId());

        MemberLikeReply create = MemberLikeReply.builder()
                .member(loginMember.get())
                .reply(reply.get())
                .build();

        memberLikeReplyRepository.save(create);

        reply.get().addLikeCount();

        replyRepository.save(reply.get());

        return toReadDto(replyRepository.findReplyByIdAndDeleteAtFalse(replyId).get());
    }

    @Override
    public List<ReplyResponseDto.READ> getLikeRepliesByMemberNickname(String memberNickname) {
        return null;
    }


    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isReply(Optional<Reply> reply) {
        if (reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
        }
    }

    private void isReplyLikeDuplicated(Long replyId, Long memberId) {
        if (memberLikeReplyRepository.existsMemberLikeReplyByReply_IdAndMember_IdAndDeleteAtFalse(replyId, memberId)) {
            throw new DuplicatedException(ResponseStatus.FAIL_Reply_LIKE_MEMBER_DUPLICATED);
        }
    }

    private static Reply toEntity(ReplyRequestDto.CREATE create, Member member, Post post) {
        Reply reply = Reply.builder()
                .post(post)
                .member(member)
                .content(create.getContent())
                .likeCount(create.getLikeCount())
                .build();
        return reply;
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
}
