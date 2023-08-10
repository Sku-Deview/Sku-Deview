package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.UniversityDto;

public interface UniversityService {

    void createUniversity(String email, UniversityDto universityDto);

    UniversityDto getUniversityMajor(UniversityDto universityDto);

    void updateUniversity(String email, UniversityDto universityDto);

    void deleteUniversity(String email);


}
