package com.project.ems.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findById(Integer id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException("Feedback with id " + id + " not found"));
    }

    @Override
    public Feedback save(Feedback feedback) {
        feedback.setSentAt(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateById(Feedback feedback, Integer id) {
        Feedback feedbackToUpdate = findById(id);
        feedbackToUpdate.setType(feedback.getType());
        feedbackToUpdate.setDescription(feedback.getDescription());
        return feedbackRepository.save(feedbackToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.delete(findById(id));
    }
}
