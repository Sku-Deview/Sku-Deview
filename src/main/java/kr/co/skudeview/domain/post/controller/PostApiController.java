package kr.co.skudeview.domain.post.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.domain.post.dto.PostRequestDto;
import kr.co.skudeview.domain.post.dto.PostResponseDto;
import kr.co.skudeview.domain.post.service.PostService;
import kr.co.skudeview.global.model.ResponseFormat;
import kr.co.skudeview.global.model.ResponseStatus;
import kr.co.skudeview.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
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
                                           @RequestBody @Valid PostRequestDto.CREATE createParams) throws IOException {
        Long postId = postService.createPost(userDetails.getUsername(), createParams);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_CREATE, postId);
    }
//    public Long createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
//                           @RequestBody @Valid PostRequestDto.CREATE createParams) throws IOException {
//        return postService.createPost(userDetails.getUsername(), createParams);
//
//
//    }


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
//    @PatchMapping("/post/{id}")
//    public Long updatePost(@AuthenticationPrincipal CustomUserDetails userDetails,
//                           @PathVariable Long id,
//                           @RequestBody @Valid PostRequestDto.UPDATE updateParams) {
//
//        return postService.updatePost(userDetails.getUsername(), id, updateParams);
//    }

    /**
     * Delete Post API
     *
     * @param id
     * @return ResponseStatus.SUCCESS_OK + Long postId
     */
    @DeleteMapping("/post/{id}")
    public ResponseFormat<Long> deletePost(@PathVariable Long id) {
        Long postId = postService.deletePost(id);

        return ResponseFormat.successWithData(kr.co.skudeview.global.model.ResponseStatus.SUCCESS_OK, postId);
    }

    /**
     * Read Post API - 모든 Post 다중 조회
     *
     * @return ResponseStatus.SUCCESS_OK + List<PostResponseDto.READ>
     */
//    @GetMapping("/post")
//    public ResponseFormat<List<PostResponseDto.READ>> getAllPosts() {
//        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getAllPosts());
//    }
//    @GetMapping("/post")
//    public List<PostResponseDto.READ> getAllPosts() {
//        return postService.getAllPosts();
//    }


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
//    @GetMapping("/post/{id}")
//    public PostResponseDto.READ getPostDetail(@PathVariable Long id) {
//        return postService.getPostDetail(id);
//    }

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
     * @param pageable
     * @return
     */

    @GetMapping("/posts")
    public ResponseFormat<Page<PostResponseDto.READ>> getPagedPosts(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        log.info("test={}",postService.searchPostWithPaging(pageable));
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,postService.searchPostWithPaging(pageable));

    }

}
