package com.project.ems.trainer;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.ems.constants.Constants.TRAINER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final ExperienceService experienceService;
    private final StudyService studyService;
    private final ModelMapper modelMapper;

    @Override
    public List<TrainerDto> findAll() {
        return convertToDtos(trainerRepository.findAll());
    }

    @Override
    public List<TrainerDto> findAllActive() {
        return convertToDtos(trainerRepository.findAllByIsActiveTrue());
    }

    @Override
    public TrainerDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public TrainerDto save(TrainerDto trainerDto) {
        Trainer trainerToSave = convertToEntity(trainerDto);
        trainerToSave.setIsActive(true);
        return convertToDto(trainerRepository.save(trainerToSave));
    }

    @Override
    public TrainerDto updateById(TrainerDto trainerDto, Integer id) {
        Trainer trainerToUpdate = findEntityById(id);
        updateEntityFromDto(trainerToUpdate, trainerDto);
        return convertToDto(trainerRepository.save(trainerToUpdate));
    }

    @Override
    public TrainerDto disableById(Integer id) {
        Trainer trainerToDisable = findEntityById(id);
        trainerToDisable.setIsActive(false);
        return convertToDto(trainerRepository.save(trainerToDisable));
    }

    @Override
    public List<TrainerDto> convertToDtos(List<Trainer> trainers) {
        return trainers.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Trainer> convertToEntities(List<TrainerDto> trainerDtos) {
        return trainerDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public TrainerDto convertToDto(Trainer trainer) {
        TrainerDto trainerDto = modelMapper.map(trainer, TrainerDto.class);
        Trainer supervisingTrainer = trainer.getSupervisingTrainer();
        trainerDto.setAuthoritiesIds(trainer.getAuthorities().stream().map(Authority::getId).toList());
        trainerDto.setExperiencesIds(trainer.getExperiences().stream().map(Experience::getId).toList());
        trainerDto.setStudiesIds(trainer.getStudies().stream().map(Study::getId).toList());
        trainerDto.setSupervisingTrainerId(supervisingTrainer != null ? supervisingTrainer.getId() : null);
        return trainerDto;
    }

    @Override
    public Trainer convertToEntity(TrainerDto trainerDto) {
        Trainer trainer = modelMapper.map(trainerDto, Trainer.class);
        Integer supervisingTrainerId = trainerDto.getSupervisingTrainerId();
        trainer.setRole(roleService.findEntityById(trainerDto.getRoleId()));
        trainer.setAuthorities(trainerDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).toList());
        trainer.setExperiences(trainerDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
        trainer.setStudies(trainerDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        trainer.setSupervisingTrainer(supervisingTrainerId != null ? findEntityById(supervisingTrainerId) : null);
        return trainer;
    }

    @Override
    public Trainer findEntityById(Integer id) {
        return trainerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(TRAINER_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(Trainer trainer, TrainerDto trainerDto) {
        Integer supervisingTrainerId = trainerDto.getSupervisingTrainerId();
        trainer.setName(trainerDto.getName());
        trainer.setEmail(trainerDto.getEmail());
        trainer.setPassword(trainerDto.getPassword());
        trainer.setMobile(trainerDto.getMobile());
        trainer.setAddress(trainerDto.getAddress());
        trainer.setBirthday(trainerDto.getBirthday());
        trainer.setRole(roleService.findEntityById(trainerDto.getRoleId()));
        trainer.setAuthorities(trainerDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).collect(Collectors.toList()));
        trainer.setEmploymentType(trainerDto.getEmploymentType());
        trainer.setPosition(trainerDto.getPosition());
        trainer.setGrade(trainerDto.getGrade());
        trainer.setSalary(trainerDto.getSalary());
        trainer.setHiredAt(trainerDto.getHiredAt());
        trainer.setExperiences(trainerDto.getExperiencesIds().stream().map(experienceService::findEntityById).collect(Collectors.toList()));
        trainer.setStudies(trainerDto.getStudiesIds().stream().map(studyService::findEntityById).collect(Collectors.toList()));
        trainer.setSupervisingTrainer(supervisingTrainerId != null ? findEntityById(supervisingTrainerId) : null);
        trainer.setNrTrainees(trainerDto.getNrTrainees());
        trainer.setMaxTrainees(trainerDto.getMaxTrainees());
    }
}
