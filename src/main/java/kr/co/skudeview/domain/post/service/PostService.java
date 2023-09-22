package kr.co.skudeview.domain.post.service;

import kr.co.skudeview.domain.post.dto.PostRequestDto;
import kr.co.skudeview.domain.post.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface PostService {

    /**
     * 새로운 포스트를 생성.
     *
     * @param email  포스트를 생성한 회원의 이메일
     * @param create 생성할 포스트 정보를 담고 있는 DTO
     * @return 생성된 포스트의 ID
     * @throws IOException 포스트 생성 중 발생한 예외 처리
     */
    Long createPost(String email, PostRequestDto.CREATE create) throws IOException;

    /**
     * 특정 포스트를 수정.
     *
     * @param email  포스트를 업데이트할 회원의 이메일
     * @param postId 업데이트할 포스트의 ID
     * @param update 업데이트할 포스트 정보를 담고 있는 DTO
     * @return 업데이트된 포스트의 ID
     */
    Long updatePost(String email, final Long postId, final PostRequestDto.UPDATE update);

    /**
     * 포스트를 삭제.
     *
     * @param postId 삭제할 포스트의 ID
     * @return 삭제된 포스트의 ID
     */
    Long deletePost(final Long postId);

    /**
     * 특정 포스트의 상세 정보를 조회.
     *
     * @param postId 조회할 포스트의 ID
     * @return 조회된 포스트의 상세 정보를 담은 DTO (PostResponseDto.READ)
     */
    PostResponseDto.READ getPostDetail(final Long postId);

    /**
     * 페이징을 사용하여 조건에 맞는 포스트 목록을 검색.
     *
     * @param pageable 페이징 정보
     * @return 페이징된 포스트 목록을 담은 DTO 페이지 (Page<PostResponseDto.READ>)
     */
    Page<PostResponseDto.READ> searchPostWithPaging(Pageable pageable, String postCategory, String searchType, String searchText);

    /**
     * PostCategory가 Notice인 포스트 목록 조회
     *
     * @return PostCategory == NOTICE  List<PostResponseDto.READ>
     */
    List<PostResponseDto.READ> searchNoticePost();
    /**
     * Redis에 포스트 조회수를 업데이트.
     *
     * @param postId  포스트의 ID
     * @param hashKey Redis에서 사용할 해시 키
     */
    void updateCntToRedis(final Long postId, String hashKey);

    /**
     * updateCntToRedis이후, Redis에서 포스트 조회수를 삭제.
     */
    void deleteCntToRedis();
}
