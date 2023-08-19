package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.PostService;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import kr.co.skudeview.service.model.custom.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * Create Post API
     *
     * @param createParams
     * @return ResponseStatus.SUCCESS_CREATE + Long postId
     */
    @PostMapping("/post")
    public ResponseFormat<Long> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestBody @Valid PostRequestDto.CREATE createParams) {
        Long postId = postService.createPost(userDetails.getUsername(), createParams);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_CREATE, postId);
    }

    /**
     * Update Post API
     *
     * @param id
     * @param updateParams
     * @return ResponseStatus.SUCCESS_OK + Long postId
     */
    @PatchMapping("/post/{id}")
    public ResponseFormat<Long> updatePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable Long id,
                                           @RequestBody @Valid PostRequestDto.UPDATE updateParams) {
        Long postId = postService.updatePost(userDetails.getUsername(), id, updateParams);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postId);
    }

    /**
     * Delete Post API
     *
     * @param id
     * @return ResponseStatus.SUCCESS_OK + Long postId
     */
    @DeleteMapping("/post/{id}")
    public ResponseFormat<Long> deletePost(@PathVariable Long id) {
        Long postId = postService.deletePost(id);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postId);
    }

    /**
     * Read Post API - 모든 Post 다중 조회
     *
     * @return ResponseStatus.SUCCESS_OK + List<PostResponseDto.READ>
     */
    @GetMapping("/post")
    public ResponseFormat<List<PostResponseDto.READ>> getAllPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getAllPosts());
    }

    /**
     * Read Post API - postId 값으로 단일 조회
     *
     * @param id
     * @return ResponseStatus.SUCCESS_OK + PostResponseDto.READ
     */
    @GetMapping("/post/{id}")
    public ResponseFormat<PostResponseDto.READ> getPostDetail(@PathVariable Long id) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPostDetail(id));
    }

    /**
     * Search Post API - 검색 조건에 맞는 post 다중 조회
     *
     * @param condition
     * @return ResponseStatus.SUCCESS_OK + List<PostResponseDto.READ>
     */
    @GetMapping("/post/search")
    public ResponseFormat<List<PostResponseDto.READ>> getSearchPosts(@RequestBody @Valid PostRequestDto.CONDITION condition) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getSearchPosts(condition));
    }

    /**
     * Search Post API + Paging default page=0, size=20
     * http://localhost:8080/api/v1/posts?page=0&size=10 -> 이런식으로 지정해서 사용도 가능
     *
     * @param condition
     * @param pageable
     * @return
     */
    @GetMapping("/posts")
    public ResponseFormat<Page<PostResponseDto.READ>> getPagedPosts(@RequestBody @Valid PostRequestDto.CONDITION condition, Pageable pageable) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.searchPostWithPaging(condition, pageable));
    }




}
