package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Post;
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

        Post post = Post.builder()
                .member(findMember.get())
                .title(create.getTitle())
                .content(create.getContent())
                .likeCount(create.getLikeCount())
                .viewCount(create.getViewCount())
                .postCategory(create.getPostCategory())
                .build();

        postRepository.save(post);

        return post.getId();
    }

    @Override
    public List<PostResponseDto.READ> getAllPosts() {
        List<Post> list = postRepository.findAllByDeleteAtFalse(); //추후 삭제 여부에 따른 동적쿼리로 변경
        List<PostResponseDto.READ> posts = new ArrayList<>();

        for (Post post : list) {
            PostResponseDto.READ dto = PostResponseDto.READ.builder()
                    .postId(post.getId())
                    .memberEmail(post.getMember().getEmail())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .postCategory(post.getPostCategory())
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .build();
            posts.add(dto);
        }
        return posts;
    }

    @Override
    @Transactional
    public Long updatePost(Long postId, PostRequestDto.UPDATE update) {
        Post post = postRepository.findPostByIdAndDeleteAtFalse(postId).orElseThrow(() -> new RuntimeException());
        post.updatePost(update.getTitle(),update.getContent(), update.getPostCategory());
        return postId;
    }

    @Override
    @Transactional
    public Long deletePost(Long postId) {
        Post post = postRepository.findPostByIdAndDeleteAtFalse(postId).orElseThrow(() -> new RuntimeException());
//        post.delete();
        post.changeDeleteAt();

        postRepository.save(post);

        return post.getId();
    }

    /**
     * 게시글 상세정보 조회
     */
    @Override
    @Transactional
    public PostResponseDto.READ getPostDetail(Long postId) {
        Post post = postRepository.findPostByIdAndDeleteAtFalse(postId).orElseThrow(() -> new RuntimeException());

        post.increaseViewCount();

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

}
