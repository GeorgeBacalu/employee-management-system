package com.project.ems.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDto> findAll();

    Page<FeedbackDto> findAllByKey(Pageable pageable, String key);

    FeedbackDto findById(Integer id);

    FeedbackDto save(FeedbackDto feedbackDto);

    FeedbackDto updateById(FeedbackDto feedbackDto, Integer id);

    void deleteById(Integer id);

    List<FeedbackDto> convertToDtos(List<Feedback> feedbacks);

    List<Feedback> convertToEntities(List<FeedbackDto> feedbackDtos);

    FeedbackDto convertToDto(Feedback feedback);

    Feedback convertToEntity(FeedbackDto feedbackDto);

    Feedback findEntityById(Integer id);
}
