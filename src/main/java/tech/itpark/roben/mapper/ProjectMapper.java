package tech.itpark.roben.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.roben.domain.Project;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements RowMapper<Project> {
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Project(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
