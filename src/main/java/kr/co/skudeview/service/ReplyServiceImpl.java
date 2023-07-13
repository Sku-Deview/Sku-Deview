package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.Reply;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.PostRepository;
import kr.co.skudeview.repository.ReplyRepository;
import kr.co.skudeview.service.dto.request.ReplyRequestDto;
import kr.co.skudeview.service.dto.response.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService{

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public Long createReply(Long postId,ReplyRequestDto.CREATE create) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(create.getMemberEmail());

        isMember(findMember);

        Optional<Post> findPost = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(findPost);

        Reply reply = Reply.builder()
                .post(findPost.get())
                .member(findMember.get())
                .content(create.getContent())
                .likeCount(create.getLikeCount())
                .build();

        replyRepository.save(reply);

        return reply.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDto.READ> getAllReplies(Long postId) {
        List<Reply> list = replyRepository.findRepliesByPostIdAndDeleteAtFalse(postId);
        List<ReplyResponseDto.READ> replies = new ArrayList<>();

        for (Reply reply : list) {
            ReplyResponseDto.READ dto = ReplyResponseDto.READ.builder()
                    .replyId(reply.getId())
                    .memberEmail(reply.getMember().getEmail())
                    .postId(reply.getPost().getId())
                    .content(reply.getContent())
                    .likeCount(reply.getLikeCount())
                    .build();
            replies.add(dto);
        }

        return replies;
    }

    @Override
    @Transactional
    public Long updateReply(Long replyId,ReplyRequestDto.UPDATE update) {
        Optional<Reply> reply = replyRepository.findReplyByIdAndDeleteAtFalse(replyId);

        isReply(reply);

        reply.get().updateReply(update.getContent());

        return reply.get().getId();
    }

    @Override
    @Transactional
    public Long deleteReply(Long postId,Long replyId) {
        Optional<Reply> reply = replyRepository.findReplyByIdAndDeleteAtFalse(replyId);

        isReply(reply);

        reply.get().changeDeleteAt();

        replyRepository.save(reply.get());

        return reply.get().getId();
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

}
