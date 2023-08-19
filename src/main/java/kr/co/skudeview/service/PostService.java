package kr.co.skudeview.service;

import kr.co.skudeview.domain.Post;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface PostService {

    Long createPost(PostRequestDto.CREATE create) throws IOException;

    List<PostResponseDto.READ> getAllPosts();

    Long updatePost(final Long postId, final PostRequestDto.UPDATE update);

    Long deletePost(final Long postId);

    PostResponseDto.READ getPostDetail(final Long postId);

    List<PostResponseDto.READ> getSearchPosts(PostRequestDto.CONDITION condition);

    Page<PostResponseDto.READ> searchPostWithPaging(PostRequestDto.CONDITION condition, Pageable pageable);

//    default Post toEntity(PostRequestDto.CREATE create, Member findMember) {
//        return Post.builder()
//                .member(findMember)
//                .title(create.getTitle())
//                .content(create.getContent())
//                .likeCount(create.getLikeCount())
//                .viewCount(create.getViewCount())
//                .postCategory(create.getPostCategory())
//                .build();
//    }
}
