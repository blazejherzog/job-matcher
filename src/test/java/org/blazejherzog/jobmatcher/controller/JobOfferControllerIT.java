package org.blazejherzog.jobmatcher.controller;

import org.blazejherzog.jobmatcher.domain.job.JobOfferDuration;
import org.blazejherzog.jobmatcher.domain.job.CompanyLogo;
import org.blazejherzog.jobmatcher.web.MenuView;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@SqlGroup(value = {
        @Sql(scripts = {"classpath:/dataset/insert-four-job-offers-with-details.sql"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:/dataset/delete-all.sql"}, executionPhase = AFTER_TEST_METHOD)
})
public class JobOfferControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(Instant.parse("2024-09-13T00:00:00Z"), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    void shouldReturnJobOffersOrderedByPublishDate() throws Exception {
        mockMvc.perform(get("/jobs/offers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("job/offers"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("menu", Matchers.is(MenuView.of("job/offers"))))
                .andExpect(model().attribute("page", hasProperty("elements", containsInAnyOrder(
                        jobOffer(id(1L), title("Software Engineer")),
                        jobOffer(id(2L), title("Marketing Specialist"))
                ))));

        mockMvc.perform(get("/jobs/offers/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("job/offers"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("menu", is(MenuView.of("job/offers"))))
                .andExpect(model().attribute("page", hasProperty("elements", containsInAnyOrder(
                        jobOffer(id(3L), title("Financial Analyst")),
                        jobOffer(id(4L), title("HR Manager"))
                ))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/jobs/offers/535345", "/jobs/offers/", "/jobs/offers"})
    void shouldRedirectToDefaultPageWhenInvalidPage(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/jobs/offers/1"));
    }

    @Test
    void shouldReturnJobOfferDetails() throws Exception {
        mockMvc.perform(get("/jobs/offer/1, Software Engineer"))
                .andExpect(status().isOk())
                .andExpect(view().name("job/offer-details"))
                .andExpect(model().attributeExists("jobOffer"))
                .andExpect(model().attribute("menu", is(MenuView.of("job/offer-details"))))
                .andExpect(jobOfferHasPropertyEqualTo("id", 1L))
                .andExpect(jobOfferHasPropertyEqualTo("title", "Software Engineer"))
                .andExpect(jobOfferHasPropertyEqualTo("remainingDays", JobOfferDuration.ofDays(25)))
                .andExpect(jobOfferHasPropertyEqualTo("description", "Develop software solutions."))
                .andExpect(jobOfferHasPropertyEqualTo("companyLogo", new CompanyLogo(1L, "company_logo1.jpg")))
                .andExpect(jobOfferHasPropertyEqualTo("branch", "Engineering"))
                .andExpect(jobOfferHasPropertyEqualToValues("locations", "New York", "San Francisco"));
    }

    private static ResultMatcher jobOfferHasPropertyEqualToValues(String propertyName, String... propertyValues) {
        return model().attribute("jobOffer", hasProperty(propertyName, containsInAnyOrder(propertyValues)));
    }

    private static ResultMatcher jobOfferHasPropertyEqualTo(String propertyName, Long propertyValue) {
        return model().attribute("jobOffer", hasProperty(propertyName, equalTo(propertyValue)));
    }

    private static ResultMatcher jobOfferHasPropertyEqualTo(String propertyName, String propertyValue) {
        return model().attribute("jobOffer", hasProperty(propertyName, equalTo(propertyValue)));
    }

    private static ResultMatcher jobOfferHasPropertyEqualTo(String propertyName, CompanyLogo propertyValue) {
        return model().attribute("jobOffer", hasProperty(propertyName, equalTo(propertyValue)));
    }

    private static ResultMatcher jobOfferHasPropertyEqualTo(String propertyName, JobOfferDuration propertyValue) {
        return model().attribute("jobOffer", hasProperty(propertyName, equalTo(propertyValue)));
    }

    private static Matcher<Object> jobOffer(long id, String title) {
        return allOf(
                hasProperty("id", equalTo(id)),
                hasProperty("title", equalTo(title))
        );
    }

    private String title(String title) {
        return title;
    }

    private long id(long id) {
        return id;
    }
}