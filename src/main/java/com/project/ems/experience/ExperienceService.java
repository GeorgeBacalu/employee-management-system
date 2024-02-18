package com.project.ems.experience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExperienceService {

    List<ExperienceDto> findAll();

    Page<ExperienceDto> findAllByKey(Pageable pageable, String key);

    ExperienceDto findById(Integer id);

    ExperienceDto save(ExperienceDto experienceDto);

    ExperienceDto updateById(ExperienceDto experienceDto, Integer id);

    void deleteById(Integer id);

    List<ExperienceDto> convertToDtos(List<Experience> experiences);

    List<Experience> convertToEntities(List<ExperienceDto> experienceDtos);

    ExperienceDto convertToDto(Experience experience);

    Experience convertToEntity(ExperienceDto experienceDto);

    Experience findEntityById(Integer id);
}
