package com.project.ems.study;

import com.project.ems.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudyDto> findAll() {
        return convertToDtos(studyRepository.findAll());
    }

    @Override
    public StudyDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public StudyDto save(StudyDto studyDto) {
        return convertToDto(studyRepository.save(convertToEntity(studyDto)));
    }

    @Override
    public StudyDto updateById(StudyDto studyDto, Integer id) {
        Study studyToUpdate = findEntityById(id);
        updateEntityFromDto(studyToUpdate, studyDto);
        return convertToDto(studyRepository.save(studyToUpdate));
    }

    @Override
    public void deleteById(Integer id) {
        studyRepository.delete(findEntityById(id));
    }

    @Override
    public List<StudyDto> convertToDtos(List<Study> studies) {
        return studies.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Study> convertToEntities(List<StudyDto> studyDtos) {
        return studyDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public StudyDto convertToDto(Study study) {
        return modelMapper.map(study, StudyDto.class);
    }

    @Override
    public Study convertToEntity(StudyDto studyDto) {
        return modelMapper.map(studyDto, Study.class);
    }

    @Override
    public Study findEntityById(Integer id) {
        return studyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Study with id " + id + " not found"));
    }

    private void updateEntityFromDto(Study study, StudyDto studyDto) {
        study.setTitle(studyDto.getTitle());
        study.setInstitution(studyDto.getInstitution());
        study.setDescription(studyDto.getDescription());
        study.setType(studyDto.getType());
        study.setStartedAt(studyDto.getStartedAt());
        study.setFinishedAt(studyDto.getFinishedAt());
    }
}
