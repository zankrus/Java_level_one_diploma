package tech.itpark.roben.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.roben.domain.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestCaseMapper implements RowMapper<TestCase> {
    public TestCase mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TestCase(
                rs.getLong("id"),
                rs.getLong("suite_id"),
                rs.getString("description"),
                rs.getString("attached_files")
        );
    }
}
