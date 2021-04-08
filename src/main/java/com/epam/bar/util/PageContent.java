package com.epam.bar.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is used to implement pagination
 *
 * @param <T> the type parameter
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Getter
@EqualsAndHashCode
public class PageContent<T> {
    private static final int PER_PAGE = 10;
    private final List<T> objects;
    private final int page;
    private final int totalPages;

    /**
     * Instantiates a new Page content.
     *
     * @param objects the objects
     * @param page    the page
     */
    public PageContent(List<T> objects, int page) {
        this.objects = extractObjects(objects, page);
        this.page = page;
        this.totalPages = (int) Math.ceil((double) objects.size() / PER_PAGE);
    }

    private List<T> extractObjects(List<T> objects, int page) {
        List<T> copyObjects = new ArrayList<>();
        int start = (page - 1) * PER_PAGE;
        int end = Math.min(page * PER_PAGE, objects.size());
        for (int i = start; i < end; i++) {
            copyObjects.add(objects.get(i));
        }
        return copyObjects;
    }
}
