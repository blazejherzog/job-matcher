package org.blazejherzog.jobmatcher.web;

import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageView<T> {

    private static final int MAX_VISIBLE_PAGES = 7;

    private static final int FIRST_PAGE_NUMBER = 1;

    private static final int OFFSET = 3;

    private static final int SPRING_PAGE_OFFSET = 1;

    private final Page<T> page;

    private PageView(Page<T> page) {
        this.page = page;
    }

    public boolean isVisible() {
        return page.hasNext() || page.hasPrevious();
    }

    public List<T> getElements() {
        return page.getContent();
    }

    public boolean isFirstVisible() {
        return page.getTotalPages() > MAX_VISIBLE_PAGES;
    }

    public boolean hasFirst() {
        return !page.isFirst();
    }

    public boolean isLastVisible() {
        return page.getTotalPages() > MAX_VISIBLE_PAGES;
    }

    public int getFirst() {
        return FIRST_PAGE_NUMBER;
    }

    public boolean hasLast() {
        return !page.isLast();
    }

    public int getLast() {
        return page.getTotalPages();
    }

    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    public int getPrevious() {
        return page.previousPageable().getPageNumber() + SPRING_PAGE_OFFSET;
    }

    public boolean hasNext() {
        return page.hasNext();
    }

    public int getNext() {
        return page.nextPageable().getPageNumber() + SPRING_PAGE_OFFSET;
    }

    public List<PageNumber> getPageNumbers() {
        List<PageNumber> pageNumbers = new ArrayList<>();

        int activePageNumber = page.getNumber() + SPRING_PAGE_OFFSET;
        int lastPageNumber = page.getTotalPages();

        int lastPageNumberOffset = lastPageNumber - activePageNumber;

        int leftOffset = Math.min(OFFSET, activePageNumber);

        if (lastPageNumberOffset < OFFSET) {
            leftOffset += OFFSET - lastPageNumberOffset;
        }

        int startPageNumber = Math.max(FIRST_PAGE_NUMBER, lastPageNumber - lastPageNumberOffset - leftOffset);

        for (int i = startPageNumber; i < startPageNumber + Math.min(MAX_VISIBLE_PAGES, page.getTotalPages()); i++) {
            pageNumbers.add(i == activePageNumber ? PageNumber.active(i) : PageNumber.notActive(i));
        }

        return pageNumbers;
    }

    public static <T> PageView<T> of(@NonNull Page<T> page) {
        return new PageView<>(page);
    }

    public record PageNumber(int value, boolean isActive) {

        static PageNumber active(int value) {
            return new PageNumber(value, true);
        }

        static PageNumber notActive(int value) {
            return new PageNumber(value, false);
        }
    }
}
