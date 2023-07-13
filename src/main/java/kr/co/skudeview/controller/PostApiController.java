package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.PostService;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 생성
     */
    @PostMapping("/post")
    public ResponseFormat<Long> createPost(@RequestBody @Valid PostRequestDto.CREATE createParams) {
        Long postId = postService.createPost(createParams);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_CREATE, postId);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/post/{id}")
    public ResponseFormat<Long> updatePost(@PathVariable Long id, @RequestBody @Valid PostRequestDto.UPDATE updateParams) {
        Long postId = postService.updatePost(id, updateParams);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postId);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/post/{id}")
    public ResponseFormat<Long> deletePost(@PathVariable Long id) {
        Long postId = postService.deletePost(id);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postId);
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping("/post")
    public ResponseFormat<List<PostResponseDto.READ>> getAllPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getAllPosts());
    }

    /**
     * 게시글 상세정보 조회
     */
    @GetMapping("/post/{id}")
    public ResponseFormat<PostResponseDto.READ> getPostDetail(@PathVariable Long id) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPostDetail(id));
    }
}
