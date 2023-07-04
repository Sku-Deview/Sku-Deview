package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {


    public Long createPost(PostRequestDto.CREATE create);

    public List<PostResponseDto.READ> getAllPosts();

    public Long updatePost(final Long postId, final PostRequestDto.UPDATE update);

    public Long deletePost(final Long postId);

    public PostResponseDto.READ getPostDetail(final Long postId);
}
