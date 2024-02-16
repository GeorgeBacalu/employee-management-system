package com.project.ems.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.project.ems.constants.Constants.*;

@Controller
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public String findAllPage(Model model) {
        model.addAttribute(FEEDBACKS_ATTRIBUTE, feedbackService.convertToEntities(feedbackService.findAll()));
        return FEEDBACKS_VIEW;
    }

    @GetMapping("/{id}")
    public String findByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute(FEEDBACK_ATTRIBUTE, feedbackService.findEntityById(id));
        return FEEDBACK_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSavePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute(FEEDBACK_DTO_ATTRIBUTE, id == -1 ? new FeedbackDto() : feedbackService.findById(id));
        return SAVE_FEEDBACK_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Integer id) {
        if (id == -1) {
            feedbackService.save(feedbackDto);
        } else {
            feedbackService.updateById(feedbackDto, id);
        }
        return REDIRECT_FEEDBACKS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        feedbackService.deleteById(id);
        return REDIRECT_FEEDBACKS_VIEW;
    }
}
