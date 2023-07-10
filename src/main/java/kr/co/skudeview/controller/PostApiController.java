package kr.co.skudeview.controller;

import kr.co.skudeview.repository.PostRepository;
import kr.co.skudeview.service.PostService;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 생성
     */
    @PostMapping("/post")
    public Long savePost(@RequestBody PostRequestDto.CREATE createParams) {
        Long postId = postService.createPost(createParams);
        return postId;
    }


    /**
     * 게시글 리스트 조회
     */
    @GetMapping("/post")
    public List<PostResponseDto.READ> findAllPost() {
        List<PostResponseDto.READ> posts = postService.getAllPosts();
        return posts;

    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto.UPDATE updateParams) {
        Long postId = postService.updatePost(id, updateParams);
        return postId;
    }
    /**
     * 게시글 삭제
     */
    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        Long postId = postService.deletePost(id);
        return postId;
    }

    /**
     * 게시글 상세정보 조회
     */
    @GetMapping("/post/{id}")
    public PostResponseDto.READ findPostDetail(@PathVariable Long id) {
        PostResponseDto.READ postDetail = postService.getPostDetail(id);
        return postDetail;
    }
}
