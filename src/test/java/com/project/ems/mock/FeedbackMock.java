package com.project.ems.mock;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.enums.FeedbackType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.ems.mock.UserMock.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMock {

    public static List<Feedback> getMockedFeedbacks() {
        return List.of(getMockedFeedback1(), getMockedFeedback2(), getMockedFeedback3(), getMockedFeedback4(), getMockedFeedback5(), getMockedFeedback6(), getMockedFeedback7(), getMockedFeedback8(), getMockedFeedback9(), getMockedFeedback10(), getMockedFeedback11(), getMockedFeedback12());
    }

    public static List<FeedbackDto> getMockedFeedbackDtos() {
        return List.of(getMockedFeedbackDto1(), getMockedFeedbackDto2(), getMockedFeedbackDto3(), getMockedFeedbackDto4(), getMockedFeedbackDto5(), getMockedFeedbackDto6(), getMockedFeedbackDto7(), getMockedFeedbackDto8(), getMockedFeedbackDto9(), getMockedFeedbackDto10(), getMockedFeedbackDto11(), getMockedFeedbackDto12());
    }

    public static List<Feedback> getMockedFeedbacksPage1() {
        return List.of(getMockedFeedback1(), getMockedFeedback2());
    }

    public static List<Feedback> getMockedFeedbacksPage2() {
        return List.of(getMockedFeedback3(), getMockedFeedback4());
    }

    public static List<Feedback> getMockedFeedbacksPage3() {
        return List.of(getMockedFeedback5(), getMockedFeedback6());
    }

    public static List<FeedbackDto> getMockedFeedbackDtosPage1() {
        return List.of(getMockedFeedbackDto1(), getMockedFeedbackDto2());
    }

    public static List<FeedbackDto> getMockedFeedbackDtosPage2() {
        return List.of(getMockedFeedbackDto3(), getMockedFeedbackDto4());
    }

    public static List<FeedbackDto> getMockedFeedbackDtosPage3() {
        return List.of(getMockedFeedbackDto5(), getMockedFeedbackDto6());
    }

    public static Feedback getMockedFeedback1() {
        return Feedback.builder()
              .id(1)
              .type(FeedbackType.OPTIMIZATION)
              .description("Improve page load time for the dashboard.")
              .sentAt(LocalDateTime.of(2024, 4, 21, 9, 15))
              .user(getMockedUser1())
              .build();
    }

    public static Feedback getMockedFeedback2() {
        return Feedback.builder()
              .id(2)
              .type(FeedbackType.OPTIMIZATION)
              .description("Optimize search functionality for better results.")
              .sentAt(LocalDateTime.of(2024, 4, 25, 10, 50))
              .user(getMockedUser2())
              .build();
    }

    public static Feedback getMockedFeedback3() {
        return Feedback.builder()
              .id(3)
              .type(FeedbackType.OPTIMIZATION)
              .description("Compress images to improve page load time.")
              .sentAt(LocalDateTime.of(2024, 5, 1, 9, 45))
              .user(getMockedUser3())
              .build();
    }

    public static Feedback getMockedFeedback4() {
        return Feedback.builder()
              .id(4)
              .type(FeedbackType.OPTIMIZATION)
              .description("Implement lazy loading for faster initial load.")
              .sentAt(LocalDateTime.of(2024, 5, 4, 17, 20))
              .user(getMockedUser4())
              .build();
    }

    public static Feedback getMockedFeedback5() {
        return Feedback.builder()
              .id(5)
              .type(FeedbackType.ISSUE)
              .description("App crashes when submitting a form.")
              .sentAt(LocalDateTime.of(2024, 4, 20, 14, 30))
              .user(getMockedUser5())
              .build();
    }

    public static Feedback getMockedFeedback6() {
        return Feedback.builder()
              .id(6)
              .type(FeedbackType.ISSUE)
              .description("Error message appears when uploading an image.")
              .sentAt(LocalDateTime.of(2024, 4, 23, 11, 10))
              .user(getMockedUser6())
              .build();
    }

    public static Feedback getMockedFeedback7() {
        return Feedback.builder()
              .id(7)
              .type(FeedbackType.ISSUE)
              .description("Login issues after resetting the password.")
              .sentAt(LocalDateTime.of(2024, 4, 26, 17, 20))
              .user(getMockedUser7())
              .build();
    }

    public static Feedback getMockedFeedback8() {
        return Feedback.builder()
              .id(8)
              .type(FeedbackType.ISSUE)
              .description("Notifications not appearing on the mobile app.")
              .sentAt(LocalDateTime.of(2024, 5, 2, 16, 10))
              .user(getMockedUser8())
              .build();
    }

    public static Feedback getMockedFeedback9() {
        return Feedback.builder()
              .id(9)
              .type(FeedbackType.IMPROVEMENT)
              .description("Add a dark mode for better user experience.")
              .sentAt(LocalDateTime.of(2024, 4, 22, 16, 45))
              .user(getMockedUser9())
              .build();
    }

    public static Feedback getMockedFeedback10() {
        return Feedback.builder()
              .id(10)
              .type(FeedbackType.IMPROVEMENT)
              .description("Add more filtering options in the search bar.")
              .sentAt(LocalDateTime.of(2024, 4, 27, 15, 40))
              .user(getMockedUser10())
              .build();
    }

    public static Feedback getMockedFeedback11() {
        return Feedback.builder()
              .id(11)
              .type(FeedbackType.IMPROVEMENT)
              .description("Include an option to save items to a wishlist.")
              .sentAt(LocalDateTime.of(2024, 4, 30, 14, 15))
              .user(getMockedUser11())
              .build();
    }

    public static Feedback getMockedFeedback12() {
        return Feedback.builder()
              .id(12)
              .type(FeedbackType.IMPROVEMENT)
              .description("Allow users to customize their profile layout.")
              .sentAt(LocalDateTime.of(2024, 5, 3, 11, 35))
              .user(getMockedUser12())
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto1() {
        return FeedbackDto.builder()
              .id(1)
              .type(FeedbackType.OPTIMIZATION)
              .description("Improve page load time for the dashboard.")
              .userId(1)
              .sentAt(LocalDateTime.of(2024, 4, 21, 9, 15))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto2() {
        return FeedbackDto.builder()
              .id(2)
              .type(FeedbackType.OPTIMIZATION)
              .description("Optimize search functionality for better results.")
              .userId(2)
              .sentAt(LocalDateTime.of(2024, 4, 25, 10, 50))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto3() {
        return FeedbackDto.builder()
              .id(3)
              .type(FeedbackType.OPTIMIZATION)
              .description("Compress images to improve page load time.")
              .userId(3)
              .sentAt(LocalDateTime.of(2024, 5, 1, 9, 45))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto4() {
        return FeedbackDto.builder()
              .id(4)
              .type(FeedbackType.OPTIMIZATION)
              .description("Implement lazy loading for faster initial load.")
              .userId(4)
              .sentAt(LocalDateTime.of(2024, 5, 4, 17, 20))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto5() {
        return FeedbackDto.builder()
              .id(5)
              .type(FeedbackType.ISSUE)
              .description("App crashes when submitting a form.")
              .userId(5)
              .sentAt(LocalDateTime.of(2024, 4, 20, 14, 30))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto6() {
        return FeedbackDto.builder()
              .id(6)
              .type(FeedbackType.ISSUE)
              .description("Error message appears when uploading an image.")
              .userId(6)
              .sentAt(LocalDateTime.of(2024, 4, 23, 11, 10))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto7() {
        return FeedbackDto.builder()
              .id(7)
              .type(FeedbackType.ISSUE)
              .description("Login issues after resetting the password.")
              .userId(7)
              .sentAt(LocalDateTime.of(2024, 4, 26, 17, 20))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto8() {
        return FeedbackDto.builder()
              .id(8)
              .type(FeedbackType.ISSUE)
              .description("Notifications not appearing on the mobile app.")
              .userId(8)
              .sentAt(LocalDateTime.of(2024, 5, 2, 16, 10))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto9() {
        return FeedbackDto.builder()
              .id(9)
              .type(FeedbackType.IMPROVEMENT)
              .description("Add a dark mode for better user experience.")
              .userId(9)
              .sentAt(LocalDateTime.of(2024, 4, 22, 16, 45))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto10() {
        return FeedbackDto.builder()
              .id(10)
              .type(FeedbackType.IMPROVEMENT)
              .description("Add more filtering options in the search bar.")
              .userId(10)
              .sentAt(LocalDateTime.of(2024, 4, 27, 15, 40))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto11() {
        return FeedbackDto.builder()
              .id(11)
              .type(FeedbackType.IMPROVEMENT)
              .description("Include an option to save items to a wishlist.")
              .userId(11)
              .sentAt(LocalDateTime.of(2024, 4, 30, 14, 15))
              .build();
    }

    public static FeedbackDto getMockedFeedbackDto12() {
        return FeedbackDto.builder()
              .id(12)
              .type(FeedbackType.IMPROVEMENT)
              .description("Allow users to customize their profile layout.")
              .userId(12)
              .sentAt(LocalDateTime.of(2024, 5, 3, 11, 35))
              .build();
    }
}
