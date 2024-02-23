package com.project.ems.trainer;

import com.project.ems.authority.AuthorityService;
import com.project.ems.base.BaseUserServiceImpl;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl extends BaseUserServiceImpl<Trainer, TrainerDto, TrainerRepository> implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final EmployeeRepository employeeRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository, RoleService roleService, AuthorityService authorityService, ExperienceService experienceService, StudyService studyService, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        super(trainerRepository, roleService, authorityService, experienceService, studyService, modelMapper);
        this.trainerRepository = trainerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public TrainerDto disableById(Integer id) {
        Trainer trainerToDisable = findEntityById(id);
        trainerToDisable.setIsActive(false);
        employeeRepository.findAllByTrainer(trainerToDisable).forEach(employee -> employee.setTrainer(null));
        trainerRepository.findAllBySupervisingTrainer(trainerToDisable).forEach(trainer -> trainer.setSupervisingTrainer(null));
        return convertToDto(trainerRepository.save(trainerToDisable));
    }

    @Override
    public TrainerDto convertToDto(Trainer trainer) {
        TrainerDto trainerDto = super.convertToDto(trainer);
        Trainer supervisingTrainer = trainer.getSupervisingTrainer();
        trainerDto.setSupervisingTrainerId(supervisingTrainer != null ? supervisingTrainer.getId() : null);
        return trainerDto;
    }

    @Override
    public Trainer convertToEntity(TrainerDto trainerDto) {
        Trainer trainer = super.convertToEntity(trainerDto);
        Integer supervisingTrainerId = trainerDto.getSupervisingTrainerId();
        trainer.setSupervisingTrainer(supervisingTrainerId != null ? findEntityById(supervisingTrainerId) : null);
        return trainer;
    }

    @Override
    public void updateEntityFromDto(Trainer trainer, TrainerDto trainerDto) {
        super.updateEntityFromDto(trainer, trainerDto);
        Integer supervisingTrainerId = trainerDto.getSupervisingTrainerId();
        trainer.setSupervisingTrainer(supervisingTrainerId != null ? findEntityById(supervisingTrainerId) : null);
        trainer.setNrTrainees(trainerDto.getNrTrainees());
        trainer.setMaxTrainees(trainerDto.getMaxTrainees());
    }
}
