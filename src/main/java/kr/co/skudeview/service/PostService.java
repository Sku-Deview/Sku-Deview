package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {


    public Long createPost(PostRequestDto.CREATE create);

    public List<PostResponseDto.READ> findAll();

    public Long updatePost(final Long id, final PostRequestDto.UPDATE update);

    public Long deletePost(final Long id);

    public PostResponseDto.READ findById(final Long id);
}
