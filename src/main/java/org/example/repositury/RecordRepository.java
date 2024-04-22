package org.example.repositury;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class RecordRepository {

    private static final String INSERT = "insert into record (datetime) values (?)";

    private static final String DELETE = "delete from record where id in (select id from record where datetime < ? limit ?)";

    private final JdbcTemplate jdbcTemplate;

    public RecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRecord(LocalDateTime dateTime) {
        jdbcTemplate.update(INSERT, dateTime);
    }

    public int deleteAllBefore(LocalDateTime dateTime, int limit) {
        return jdbcTemplate.update(DELETE, dateTime, limit);
    }
}
