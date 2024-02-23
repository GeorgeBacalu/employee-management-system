package com.project.ems.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.ems.constants.Constants.EXPERIENCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExperienceDto> findAll() {
        return convertToDtos(experienceRepository.findAll());
    }

    @Override
    public Page<ExperienceDto> findAllByKey(Pageable pageable, String key) {
        Page<Experience> experiencesPage = key.trim().isEmpty() ? experienceRepository.findAll(pageable) : experienceRepository.findAllByKey(pageable, key.toLowerCase());
        return experiencesPage.hasContent() ? experiencesPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public ExperienceDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public ExperienceDto save(ExperienceDto experienceDto) {
        return convertToDto(experienceRepository.save(convertToEntity(experienceDto)));
    }

    @Override
    public ExperienceDto updateById(ExperienceDto experienceDto, Integer id) {
        Experience experienceToUpdate = findEntityById(id);
        updateEntityFromDto(experienceToUpdate, experienceDto);
        return convertToDto(experienceRepository.save(experienceToUpdate));
    }

    @Override
    public void deleteById(Integer id) {
        Experience experienceToDelete = findEntityById(id);
        userRepository.findAllByExperiencesContains(experienceToDelete).forEach(user -> user.getExperiences().remove(experienceToDelete));
        experienceRepository.delete(experienceToDelete);
    }

    @Override
    public List<ExperienceDto> convertToDtos(List<Experience> experiences) {
        return experiences.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Experience> convertToEntities(List<ExperienceDto> experienceDtos) {
        return experienceDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public ExperienceDto convertToDto(Experience experience) {
        return modelMapper.map(experience, ExperienceDto.class);
    }

    @Override
    public Experience convertToEntity(ExperienceDto experienceDto) {
        return modelMapper.map(experienceDto, Experience.class);
    }

    @Override
    public Experience findEntityById(Integer id) {
        return experienceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(Experience experience, ExperienceDto experienceDto) {
        experience.setTitle(experienceDto.getTitle());
        experience.setOrganization(experienceDto.getOrganization());
        experience.setDescription(experienceDto.getDescription());
        experience.setType(experienceDto.getType());
        experience.setStartedAt(experienceDto.getStartedAt());
        experience.setFinishedAt(experienceDto.getFinishedAt());
    }
}
