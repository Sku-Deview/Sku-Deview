package kr.co.skudeview.service;

import kr.co.skudeview.domain.Post;
import kr.co.skudeview.repository.PostRepository;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import kr.co.skudeview.service.dto.response.PostResponseDto;
import kr.co.skudeview.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    @Override
    @Transactional
    public Long createPost(PostRequestDto.CREATE create) {
        Post post = postMapper.toEntity(create);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public List<PostResponseDto.READ> getAllPosts() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id","regDate");
        List<Post> list = postRepository.findAll(sort);
        return list.stream().map(PostResponseDto.READ::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long updatePost(Long postId, PostRequestDto.UPDATE update) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException());
        post.updatePost(update.getTitle(),update.getContent(), update.getPostCategory());
        return postId;
    }

    @Override
    @Transactional
    public Long deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException());
        postRepository.delete(post);
        return post.getId();
    }

    /**
     * 게시글 상세정보 조회
     */
    @Override
    @Transactional
    public PostResponseDto.READ getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException());
        post.increaseViewCount();
        return postMapper.toReadDto(post);
    }


}
