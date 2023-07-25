package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.UniversityDto;

public interface UniversityService {

    void createUniversity(Long memberId, UniversityDto universityDto);

    UniversityDto getUniversityMajor(UniversityDto universityDto);  

    void updateUniversity(Long memberId,UniversityDto universityDto);

    void deleteUniversity(Long memberId);


}
