package org.blazejherzog.jobmatcher.web;

import org.blazejherzog.jobmatcher.test.IntegerArrayArgumentConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PageViewTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            120 | 10 | 1 | '1, 2, 3, 4, 5, 6, 7'
            120 | 10 | 2 | '1, 2, 3, 4, 5, 6, 7'
            120 | 10 | 3 | '1, 2, 3, 4, 5, 6, 7'
            120 | 10 | 4 | '1, 2, 3, 4, 5, 6, 7'
            120 | 10 | 5 | '2, 3, 4, 5, 6, 7, 8'
            120 | 10 | 6 | '3, 4, 5, 6, 7, 8, 9'
            120 | 10 | 7 | '4, 5, 6, 7, 8, 9, 10'
            120 | 10 | 8 | '5, 6, 7, 8, 9, 10, 11'
            120 | 10 | 9 | '6, 7, 8, 9, 10, 11, 12'
            120 | 10 | 10 | '6, 7, 8, 9, 10, 11, 12'
            120 | 10 | 11 | '6, 7, 8, 9, 10, 11, 12'
            120 | 10 | 12 | '6, 7, 8, 9, 10, 11, 12'
            60 | 10 | 1 | '1, 2, 3, 4, 5, 6'
            60 | 10 | 2 | '1, 2, 3, 4, 5, 6'
            60 | 10 | 3 | '1, 2, 3, 4, 5, 6'
            60 | 10 | 4 | '1, 2, 3, 4, 5, 6'
            60 | 10 | 5 | '1, 2, 3, 4, 5, 6'
            60 | 10 | 6 | '1, 2, 3, 4, 5, 6'
            24 | 10 | 1 | '1, 2, 3'
            24 | 10 | 2 | '1, 2, 3'
            24 | 10 | 3 | '1, 2, 3'
            6  | 10 | 1 | '1'
            """)
    void shouldReturnPageNumbers(int total, int pageSize, int activePage, @ConvertWith(IntegerArrayArgumentConverter.class) Integer[] pageNumberValues) {
        // Given
        List<String> elements = createElementsWithSize(pageSize);
        PageView<String> pageView = PageView.of(createPage(pageNumber(activePage), pageSize(pageSize), elements, total));

        // When
        List<PageView.PageNumber> pageNumbers = pageView.getPageNumbers();

        // Then
        assertThat(pageNumbers).extracting(PageView.PageNumber::value).containsExactly(pageNumberValues);
        assertThat(pageNumbers).filteredOn(PageView.PageNumber::isActive).containsExactly(PageView.PageNumber.active(activePage));
    }

    private int pageNumber(int pageNumber) {
        return --pageNumber;
    }

    private int pageSize(int pageSize) {
        return pageSize;
    }

    private Page<String> createPage(int pageNumber, int pageSize, List<String> elements, int total) {
        return new PageImpl<>(elements, PageRequest.of(pageNumber, pageSize), total);
    }

    private static List<String> createElementsWithSize(int size) {
        List<String> elements = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            elements.add(UUID.randomUUID().toString());
        }

        return elements;
    }
}
