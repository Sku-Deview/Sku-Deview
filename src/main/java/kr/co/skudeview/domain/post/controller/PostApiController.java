package kr.co.skudeview.domain.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.co.skudeview.domain.post.dto.PostRequestDto;
import kr.co.skudeview.domain.post.dto.PostResponseDto;
import kr.co.skudeview.domain.post.service.PostService;
import kr.co.skudeview.global.model.ResponseFormat;
import kr.co.skudeview.global.model.ResponseStatus;
import kr.co.skudeview.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
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
//    @PostMapping("/post")
//    public ResponseFormat<Long> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                           @RequestBody @Valid PostRequestDto.CREATE createParams) throws IOException {
//        Long postId = postService.createPost(userDetails.getUsername(), createParams);
//
//        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_CREATE, postId);
//    }
    @PostMapping(value = "/post", consumes = {"multipart/form-data"})
    public ResponseFormat<Long> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestPart("createParams") @Valid PostRequestDto.CREATE createParams, // @RequestPart 사용
                                           @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException { // 파일 업로드 처리
        log.info("userDetails = {}", userDetails);
        log.info("createParams = {}", createParams.getContent());
        log.info("files = {}", files);
        Long postId = postService.createPost(userDetails.getUsername(), createParams, files); // 수정된 메서드 호출

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_CREATE, postId);
    }

    /*파일 링크 클릭 시 파일 저장*/
    @GetMapping("/files/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName,
                                          HttpServletRequest request) throws IOException {
        /*프로젝트 루트 경로*/
        String rootDir = System.getProperty("user.dir");

        /*file의 path를 저장 -> 클릭 시 파일로 이동*/
        Path filePath = Path.of(rootDir + "/media/" + fileName);

        /*파일의 패스를 uri로 변경하고 resource로 저장.*/
        Resource resource = new UrlResource(filePath.toUri());

        /*컨텐츠 타입을 가지고 온다.*/
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

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

        return ResponseFormat.successWithData(kr.co.skudeview.global.model.ResponseStatus.SUCCESS_OK, postId);
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
     * Search List Post API + Paging default page=0, size=10
     * http://localhost:8080/api/v1/posts?page=0&size=10 -> 이런식으로 지정해서 사용도 가능
     *
     * @param pageable
     * @return
     */
    @GetMapping("/posts")
    public ResponseFormat<Page<PostResponseDto.READ>> getPagedPosts(@PageableDefault(page = 0, size = 10) Pageable pageable
            , @RequestParam(required = false) String postCategory, @RequestParam(required = false) String searchType, @RequestParam(required = false) String searchText) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.searchPostWithPaging(pageable, postCategory, searchType, searchText));
    }

    /**
     * NOTICE 인 게시글 가져오기 위한 API
     *
     * @return
     */
    @GetMapping("/posts/notice")
    public ResponseFormat<List<PostResponseDto.READ>> getPagedPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.searchNoticePost());
    }

    /**
     * post 와 member 사이의 좋아요 연관테이블에 좋아요 정보를 넘겨주기 위한 API
     *
     * @param postId
     * @param loginNickname
     * @return
     */
    @PostMapping("/post/like/{postId}/{loginNickname}")
    public ResponseFormat<PostResponseDto.READ> addPostLike(@PathVariable Long postId,
                                                            @PathVariable String loginNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.addPostLikeByLoginNickname(postId, loginNickname));
    }


    /** Member 가 작성한 게시물들 조회를 위한 API **/
    @PostMapping("/posts/member/{memberNickname}")
    public ResponseFormat<List<PostResponseDto.READ>> getPostsByMemberNickname(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPostsByMemberNickname(memberNickname));
    }

}
