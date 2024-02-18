package com.project.ems.study;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyService {

    List<StudyDto> findAll();

    Page<StudyDto> findAllByKey(Pageable pageable, String key);

    StudyDto findById(Integer id);

    StudyDto save(StudyDto studyDto);

    StudyDto updateById(StudyDto studyDto, Integer id);

    void deleteById(Integer id);

    List<StudyDto> convertToDtos(List<Study> studies);

    List<Study> convertToEntities(List<StudyDto> studyDtos);

    StudyDto convertToDto(Study study);

    Study convertToEntity(StudyDto studyDto);

    Study findEntityById(Integer id);
}
