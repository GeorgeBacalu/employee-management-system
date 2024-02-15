package com.project.ems.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute("feedbacks", feedbackService.convertToEntities(feedbackService.findAll()));
        return "feedback/feedbacks";
    }

    @GetMapping("/{id}")
    public String finByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("feedback", feedbackService.findEntityById(id));
        return "feedback/feedback-details";
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("feedbackDto", new FeedbackDto());
        return "feedback/save-feedback";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute FeedbackDto feedbackDto) {
        feedbackService.save(feedbackDto);
        return "redirect:/feedbacks";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        model.addAttribute("feedbackDto", feedbackService.findById(id));
        return "feedback/update-feedback";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Integer id) {
        feedbackService.updateById(feedbackDto, id);
        return "redirect:/feedbacks";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        feedbackService.deleteById(id);
        return "redirect:/feedbacks";
    }
}
