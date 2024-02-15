package com.project.ems.mock;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.enums.FeedbackType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMock {

    public static List<Feedback> getMockedFeedbacks() {
        return List.of(getMockedFeedback1(), getMockedFeedback2());
    }

    public static List<FeedbackDto> getMockedFeedbackDtos() {
        return List.of(getMockedFeedbackDto1(), getMockedFeedbackDto2());
    }

    public static Feedback getMockedFeedback1() {
        return Feedback.builder()
              .id(1)
              .type(FeedbackType.ISSUE)
              .description("test_description1")
              .sentAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
              .user(getMockedUser1())
              .build();
    }

    public static Feedback getMockedFeedback2() {
        return Feedback.builder()
              .id(2)
              .type(FeedbackType.IMPROVEMENT)
              .description("test_description2")
              .sentAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
              .user(getMockedUser2())
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto1() {
        return FeedbackDto.builder()
              .id(1)
              .type(FeedbackType.ISSUE)
              .description("test_description1")
              .sentAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
              .userId(1)
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto2() {
        return FeedbackDto.builder()
              .id(2)
              .type(FeedbackType.IMPROVEMENT)
              .description("test_description2")
              .sentAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
              .userId(2)
              .build();
    }
}
