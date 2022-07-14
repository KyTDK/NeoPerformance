package com.neomechanical.neoperformance.utils;

import java.util.Collections;
import java.util.List;

public class Pagination {
    /**
     * returns a view (not a new list) of the sourceList for the
     * range based on page and pageSize
     *
     * @param sourceList The list that is being paginated
     * @param page,      page number should start from 1
     * @param pageSize   The amount of elements to put on a page
     * @return custom error can be given instead of returning emptyList
     */
    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if (pageSize <= 0 || page <= 0) {
            return null;
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
