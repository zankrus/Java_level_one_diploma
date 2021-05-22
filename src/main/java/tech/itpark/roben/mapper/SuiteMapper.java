package tech.itpark.roben.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.roben.domain.Suite;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuiteMapper implements RowMapper<Suite> {
    public Suite mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Suite(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("project_id")
        );
    }
}
