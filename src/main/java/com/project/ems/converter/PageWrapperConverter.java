package com.project.ems.converter;

import com.project.ems.wrapper.PageWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageWrapperConverter {

    public static <T> PageWrapper<T> convertToWrapper(Page<T> page) {
        return new PageWrapper<>(
              PageRequest.of(page.getNumber(), Math.max(page.getSize(), 2), page.getSort()),
              page.getContent(),
              page.isLast(),
              page.getTotalPages(),
              page.getTotalElements(),
              page.getSize(),
              page.getNumber(),
              Sort.by(Sort.Order.asc("id")),
              page.isFirst(),
              page.getNumberOfElements(),
              page.isEmpty());
    }
}
