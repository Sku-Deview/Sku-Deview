package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.enums.PostCategory;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.PostRepository;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import kr.co.skudeview.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final PostMapper postMapper;

    @Override
    @Transactional
    public Long createPost(PostRequestDto.CREATE create) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(create.getMemberEmail());

        isMember(findMember);
        isPostCategory(String.valueOf(create.getPostCategory()));

        Post post = postMapper.toEntity(create, findMember.get());

        postRepository.save(post);

        return post.getId();
    }

    @Override
    public List<PostResponseDto.READ> getAllPosts() {
        List<Post> list = postRepository.findAllByDeleteAtFalse(); //추후 삭제 여부에 따른 동적쿼리로 변경
        List<PostResponseDto.READ> posts = new ArrayList<>();

        for (Post post : list) {
//            PostResponseDto.READ dto = PostResponseDto.READ.builder()
//                    .postId(post.getId())
//                    .memberEmail(post.getMember().getEmail())
//                    .title(post.getTitle())
//                    .content(post.getContent())
//                    .postCategory(post.getPostCategory())
//                    .viewCount(post.getViewCount())
//                    .likeCount(post.getLikeCount())
//                    .build();
            PostResponseDto.READ dto = postMapper.toReadDto(post);

            posts.add(dto);
        }
        return posts;
    }

    @Override
    @Transactional
    public Long updatePost(Long postId, PostRequestDto.UPDATE update) {
        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(post);

        post.get().updatePost(update.getTitle(),update.getContent(), update.getPostCategory());

        return postId;
    }

    @Override
    @Transactional
    public Long deletePost(Long postId) {
        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(post);

        post.get().changeDeleteAt();

        postRepository.save(post.get());

        return post.get().getId();
    }

    /**
     * 게시글 상세정보 조회
     */
    @Override
    @Transactional
    public PostResponseDto.READ getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(post);

        post.get().increaseViewCount();

//        PostResponseDto.READ dto = PostResponseDto.READ.builder()
//                .postId(post.getId())
//                .memberEmail(post.getMember().getEmail())
//                .title(post.getTitle())
//                .content(post.getContent())
//                .postCategory(post.getPostCategory())
//                .viewCount(post.getViewCount())
//                .likeCount(post.getLikeCount())
//                .build();
        PostResponseDto.READ dto = postMapper.toReadDto(post.get());

        return dto;
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

    private void isPostCategory(String category) {
        PostCategory.of(category);
    }

}
