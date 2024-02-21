package com.project.ems.feedback;

import com.project.ems.wrapper.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.util.PageUtil.*;

@Controller
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public String findAllPage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<FeedbackDto> feedbackDtosPage = feedbackService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrFeedbacks = feedbackDtosPage.getTotalElements();
        int nrPages = feedbackDtosPage.getTotalPages();
        model.addAttribute(FEEDBACKS_ATTRIBUTE, feedbackService.convertToEntities(feedbackDtosPage.getContent()));
        model.addAttribute("nrFeedbacks", nrFeedbacks);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("key", key);
        model.addAttribute("pageStartIndex", getPageStartIndex(page, size));
        model.addAttribute("pageEndIndex", getPageEndIndex(page, size, nrFeedbacks));
        model.addAttribute("pageNavigationStartIndex", getPageNavigationStartIndex(page, nrPages));
        model.addAttribute("pageNavigationEndIndex", getPageNavigationEndIndex(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(page, size, field + "," + direction, key));
        return FEEDBACKS_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        return REDIRECT_FEEDBACKS_VIEW;
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
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        feedbackService.deleteById(id);
        Page<FeedbackDto> feedbackDtosPage = feedbackService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", Math.max(0, page - (feedbackDtosPage.hasContent() ? 1 : 0)));
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        redirectAttributes.addAttribute("key", key);
        return REDIRECT_FEEDBACKS_VIEW;
    }
}
