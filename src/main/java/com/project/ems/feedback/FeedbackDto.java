package com.project.ems.feedback;

import com.project.ems.feedback.enums.FeedbackType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDto {

    private Integer id;

    private FeedbackType type;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime sentAt;

    private Integer userId;
}
