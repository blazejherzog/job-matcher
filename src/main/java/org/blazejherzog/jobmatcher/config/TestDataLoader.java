package org.blazejherzog.jobmatcher.config;

import org.blazejherzog.jobmatcher.infrastructure.user.UserAuthority;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
@Profile({"dev", "!prod"})
@ConditionalOnProperty(name = "jobmatcher.test-data.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class TestDataLoader {

    private final TestDataProperties properties;

    private final JdbcTemplate jdbcTemplate;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadTestData() {

        jdbcTemplate.update("INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (?, ?, ?, ?)",
                1, "company_logo1.jpg", "image/jpeg", LocalDateTime.of(2024, 1, 1, 10, 0));
        jdbcTemplate.update("INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (?, ?, ?, ?)",
                2, "company_logo2.jpg", "image/jpeg", LocalDateTime.of(2024, 1, 1, 11, 0));
        jdbcTemplate.update("INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (?, ?, ?, ?)",
                3, "company_logo3.jpg", "image/jpeg", LocalDateTime.of(2024, 1, 1, 12, 0));
        jdbcTemplate.update("INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (?, ?, ?, ?)",
                4, "company_logo4.jpg", "image/jpeg", LocalDateTime.of(2024, 1, 1, 13, 0));

        jdbcTemplate.update("INSERT INTO JOB_BRANCHES(id, name) VALUES (?, ?)", 1, "Engineering");
        jdbcTemplate.update("INSERT INTO JOB_BRANCHES(id, name) VALUES (?, ?)", 2, "Marketing");
        jdbcTemplate.update("INSERT INTO JOB_BRANCHES(id, name) VALUES (?, ?)", 3, "Finance");
        jdbcTemplate.update("INSERT INTO JOB_BRANCHES(id, name) VALUES (?, ?)", 4, "Human Resources");

        jdbcTemplate.update("INSERT INTO JOB_LOCATIONS(id, name) VALUES (?, ?)", 1, "New York");
        jdbcTemplate.update("INSERT INTO JOB_LOCATIONS(id, name) VALUES (?, ?)", 2, "San Francisco");
        jdbcTemplate.update("INSERT INTO JOB_LOCATIONS(id, name) VALUES (?, ?)", 3, "London");
        jdbcTemplate.update("INSERT INTO JOB_LOCATIONS(id, name) VALUES (?, ?)", 4, "Berlin");

        jdbcTemplate.update("""
            INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """, 1, "Software Engineer", "Develop software solutions.", 30, 1, 1, LocalDateTime.of(2024, 9, 8, 9, 30));
        jdbcTemplate.update("""
            INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """, 2, "Marketing Specialist", "Create marketing campaigns.", 45, 2, 2, LocalDateTime.of(2024, 9, 7, 10, 30));
        jdbcTemplate.update("""
            INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """, 3, "Financial Analyst", "Analyze financial data.", 60, 3, 3, LocalDateTime.of(2024, 9, 6, 11, 0));
        jdbcTemplate.update("""
            INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """, 4, "HR Manager", "Manage recruitment and employee relations.", 15, 4, 4, LocalDateTime.of(2024, 9, 5, 14, 0));

        jdbcTemplate.update("INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (?, ?)", 1, 1);
        jdbcTemplate.update("INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (?, ?)", 1, 2);
        jdbcTemplate.update("INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (?, ?)", 2, 3);
        jdbcTemplate.update("INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (?, ?)", 3, 1);
        jdbcTemplate.update("INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (?, ?)", 4, 4);
        jdbcTemplate.update("INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (?, ?)", 4, 3);

        loadTestUsers();
    }

    private void loadTestUsers() {
        for (TestDataProperties.User user : properties.getUsers()) {
            long userId = getNextval("t_users_id_seq");

            jdbcTemplate.update("""
            INSERT INTO t_users(id, username, first_name, last_name, password, enabled) VALUES (?, ?, ?, ?, ?, ?)
            """, userId, user.getUsername(), user.getFirstName(), user.getLastName(), passwordEncoder.encode(user.getPassword()), user.isEnabled());

            for (UserAuthority userAuthority : user.getAuthorities()) {
                long authorityId = getNextval("t_user_authorities_id_seq");
                jdbcTemplate.update("""
                INSERT INTO t_user_authorities(id, username, authority) VALUES (?, ?, ?)
                """, authorityId, user.getUsername(), userAuthority.name());
            }
        }
    }

    private Long getNextval(String sequenceName) {
        return jdbcTemplate.query(String.format("SELECT nextval('%s')", sequenceName),
                rs -> {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new SQLException("Unable to retrieve value from sequence: " + sequenceName);
                    }
                });
    }
}
