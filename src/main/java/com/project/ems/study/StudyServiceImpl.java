package com.project.ems.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;

    @Override
    public List<Study> findAll() {
        return studyRepository.findAll();
    }

    @Override
    public Study findById(Integer id) {
        return studyRepository.findById(id).orElseThrow(() -> new RuntimeException("Study with id " + id + " not found"));
    }

    @Override
    public Study save(Study study) {
        return studyRepository.save(study);
    }

    @Override
    public Study updateById(Study study, Integer id) {
        Study studyToUpdate = findById(id);
        studyToUpdate.setTitle(study.getTitle());
        studyToUpdate.setInstitution(study.getInstitution());
        studyToUpdate.setDescription(study.getDescription());
        studyToUpdate.setType(study.getType());
        studyToUpdate.setStartedAt(study.getStartedAt());
        studyToUpdate.setFinishedAt(study.getFinishedAt());
        return studyRepository.save(studyToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        studyRepository.delete(findById(id));
    }
}