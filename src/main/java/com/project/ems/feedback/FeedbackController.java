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

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("feedbackDto", id == -1 ? new FeedbackDto() : feedbackService.findById(id));
        return "feedback/save-feedback";
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Integer id) {
        if (id == -1) {
            feedbackService.save(feedbackDto);
        } else {
            feedbackService.updateById(feedbackDto, id);
        }
        return "redirect:/feedbacks";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        feedbackService.deleteById(id);
        return "redirect:/feedbacks";
    }
}
