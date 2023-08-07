package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.enums.PostCategory;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.PostRepository;
import kr.co.skudeview.repository.search.PostSearchRepository;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final PostSearchRepository postSearchRepository;

    @Override
    @Transactional
    public Long createPost(String email,PostRequestDto.CREATE create) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        isMember(findMember);

        isPostCategory(String.valueOf(create.getPostCategory()));

        Post post = toEntity(create, findMember.get());

        postRepository.save(post);

        return post.getId();
    }

    @Override
    public List<PostResponseDto.READ> getAllPosts() {
        List<Post> list = postRepository.findAllByDeleteAtFalse(); //추후 삭제 여부에 따른 동적쿼리로 변경
        List<PostResponseDto.READ> posts = new ArrayList<>();

        for (Post post : list) {
            PostResponseDto.READ dto = toDto(post);

            posts.add(dto);
        }
        return posts;
    }

    @Override
    @Transactional
    public Long updatePost(String email,Long postId, PostRequestDto.UPDATE update) {
        //email을 통한 인가 추가 예정(프론트에서 진행해야 할 것으로 생각 중 논의 예정)

        Optional<Post> post = postRepository.findPostByIdAndDeleteAtFalse(postId);

        isPost(post);

        post.get().updatePost(update.getTitle(), update.getContent(), update.getPostCategory());

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

        PostResponseDto.READ dto = toDto(post.get());

        return dto;
    }

    @Override
    public List<PostResponseDto.READ> getSearchPosts(PostRequestDto.CONDITION condition) {
        final List<Post> posts = postSearchRepository.find(condition);

        return posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    //    @Override
//    public Page<PostResponseDto.READ> searchPostWithPaging(PostRequestDto.CONDITION condition, Pageable pageable) {
////        Pageable pageable = PageRequest.of(page - 1, size);
//
//        Page<Post> postPage = postSearchRepository.findWithPaging(condition, pageable);
//
//        // Post 엔티티를 PostResponseDto.READ로 변환하여 반환
//        return postPage.map(this::toDto);
//    }
    @Override
    public Page<PostResponseDto.READ> searchPostWithPaging(PostRequestDto.CONDITION condition, Pageable pageable) {
        return postSearchRepository.findWithPaging(condition, pageable).map(this::toDto);
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

    private PostResponseDto.READ toDto(Post post) {
        PostResponseDto.READ dto = PostResponseDto.READ.builder()
                .postId(post.getId())
                .memberEmail(post.getMember().getEmail())
                .title(post.getTitle())
                .content(post.getContent())
                .postCategory(post.getPostCategory())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .build();
        return dto;
    }

    private static Post toEntity(PostRequestDto.CREATE create, Member member) {
        Post post = Post.builder()
                .member(member)
                .title(create.getTitle())
                .content(create.getContent())
                .likeCount(create.getLikeCount())
                .viewCount(create.getViewCount())
                .postCategory(create.getPostCategory())
                .build();
        return post;
    }
}
