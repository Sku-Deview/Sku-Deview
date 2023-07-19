//package kr.co.skudeview.service.mapper;
//
//
//import kr.co.skudeview.domain.Post;
//
//import kr.co.skudeview.service.dto.request.PostRequestDto;
//import kr.co.skudeview.service.dto.response.PostResponseDto;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface PostMapper {
//
//        Post toEntity(PostRequestDto.CREATE create);
//
//        @Mapping(target = "postId", source = "id")
//        PostResponseDto.READ toReadDto(Post post);
//
//
//}
//
