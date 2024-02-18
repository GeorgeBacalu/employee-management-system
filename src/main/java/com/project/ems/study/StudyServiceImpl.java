package com.project.ems.study;

import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.ems.constants.Constants.STUDY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;
    private final EmployeeRepository employeeRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudyDto> findAll() {
        return convertToDtos(studyRepository.findAll());
    }

    @Override
    public Page<StudyDto> findAllByKey(Pageable pageable, String key) {
        Page<Study> studiesPage = key.trim().isEmpty() ? studyRepository.findAll(pageable) : studyRepository.findAllByKey(pageable, key.toLowerCase());
        return studiesPage.hasContent() ? studiesPage.map(this::convertToDto) : Page.empty();
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
        Study studyToDelete = findEntityById(id);
        employeeRepository.findAllByStudiesContains(studyToDelete).forEach(employee -> employee.getStudies().remove(studyToDelete));
        trainerRepository.findAllByStudiesContains(studyToDelete).forEach(trainer -> trainer.getStudies().remove(studyToDelete));
        studyRepository.delete(studyToDelete);
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
        return studyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(STUDY_NOT_FOUND, id)));
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
