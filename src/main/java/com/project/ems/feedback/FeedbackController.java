package com.project.ems.feedback;

import com.project.ems.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACK_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_FEEDBACK_VIEW;
import static com.project.ems.mapper.FeedbackMapper.convertToEntity;
import static com.project.ems.mapper.FeedbackMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllFeedbacksPage(Model model) {
        model.addAttribute("feedbacks", convertToEntityList(modelMapper, feedbackService.findAll(), userService));
        return FEEDBACKS_VIEW;
    }

    @GetMapping("/{id}")
    public String getFeedbackByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("feedback", convertToEntity(modelMapper, feedbackService.findById(id), userService));
        return FEEDBACK_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveFeedbackPage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("feedbackDto", id == -1 ? new FeedbackDto() : feedbackService.findById(id));
        return SAVE_FEEDBACK_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute FeedbackDto feedbackDto, @PathVariable Integer id) {
        if(id == -1) {
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
