package com.project.ems.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {

    public static String getSortField(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).findFirst().orElse(pageable.getSort().toString());
    }

    public static String getSortDirection(Pageable pageable) {
        return pageable.getSort().stream().map(order -> order.getDirection().name().toLowerCase()).findFirst().orElse(pageable.getSort().toString().toLowerCase());
    }

    public static int getPageStartIndex(int page, int size) {
        return page * size + 1;
    }

    public static long getPageEndIndex(int page, int size, long nrElements) {
        return Math.min((long) (page + 1) * size, nrElements);
    }

    public static int getPageNavigationStartIndex(int page, int nrPages) {
        return page > 0 ? (page < nrPages - 1 ? page - 1 : (page == 1 ? nrPages - 2 : nrPages - 3)) : 0;
    }

    public static int getPageNavigationEndIndex(int page, int nrPages) {
        return page > 0 ? (page < nrPages - 1 ? page + 1 : nrPages - 1) : (nrPages > 3 ? 2 : nrPages - 1);
    }
}
