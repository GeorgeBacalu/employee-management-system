package com.project.ems.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.ems.constants.Constants.FEEDBACK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<FeedbackDto> findAll() {
        return convertToDtos(feedbackRepository.findAll());
    }

    @Override
    public FeedbackDto findById(Integer id) {
        return convertToDto(findEntityById(id));
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedbackToSave = convertToEntity(feedbackDto);
        feedbackToSave.setSentAt(LocalDateTime.now());
        return convertToDto(feedbackRepository.save(feedbackToSave));
    }

    @Override
    public FeedbackDto updateById(FeedbackDto feedbackDto, Integer id) {
        Feedback feedbackToUpdate = findEntityById(id);
        updateEntityFromDto(feedbackToUpdate, feedbackDto);
        return convertToDto(feedbackRepository.save(feedbackToUpdate));
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.delete(findEntityById(id));
    }

    @Override
    public List<FeedbackDto> convertToDtos(List<Feedback> feedbacks) {
        return feedbacks.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Feedback> convertToEntities(List<FeedbackDto> feedbackDtos) {
        return feedbackDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public FeedbackDto convertToDto(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDto.class);
    }

    @Override
    public Feedback convertToEntity(FeedbackDto feedbackDto) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        feedback.setUser(userService.findEntityById(feedbackDto.getUserId()));
        return feedback;
    }

    @Override
    public Feedback findEntityById(Integer id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(Feedback feedback, FeedbackDto feedbackDto) {
        feedback.setType(feedbackDto.getType());
        feedback.setDescription(feedbackDto.getDescription());
    }
}
