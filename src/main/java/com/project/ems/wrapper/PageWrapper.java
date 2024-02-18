package com.project.ems.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageWrapper<T> {

    @JsonIgnore
    private Pageable pageable;

    private List<T> content;

    private boolean last;

    private int totalPages;

    private long totalElements;

    private int size;

    private int number;

    @JsonIgnore
    private Sort sort;

    private boolean first;

    private int numberOfElements;

    private boolean empty;
}
