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
    public List<PostResponseDto.READ> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id","regDate");
        List<Post> list = postRepository.findAll(sort);
        return list.stream().map(PostResponseDto.READ::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long updatePost(Long id, PostResponseDto.READ read) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException());
        post.updatePost(read.getTitle(),read.getContent(), read.getPostCategory());
        return id;
    }

    @Override
    @Transactional
    public Long deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException());
        postRepository.delete(post);
        return post.getId();
    }

    /**
     * 게시글 상세정보 조회
     */
    @Override
    public PostResponseDto.READ findById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException());
        post.increaseViewCount();
        return postMapper.toReadDto(post);
    }


}
