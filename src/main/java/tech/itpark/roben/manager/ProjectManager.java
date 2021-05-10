package tech.itpark.roben.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import tech.itpark.roben.domain.Project;
import tech.itpark.roben.exceptions.ConstraintException;
import tech.itpark.roben.mapper.ProjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProjectManager {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProjectMapper mapper = new ProjectMapper();

    public List<Project> getAll() {
        return jdbcTemplate.query(
                // language=SQL
                "SELECT id, name FROM projects",
                mapper
        );
    }

    public Project getByID(long id) {
        return jdbcTemplate.queryForObject("SELECT id, name FROM projects where id = :id",
                Map.of("id",id),mapper
                );
    }

    public List<Project> lastItem() {
        return jdbcTemplate.query(
                // language=SQL
                "SELECT id, name FROM projects  ORDER BY id DESC LIMIT 1",
                (rs, rowNum) -> new Project(
                        rs.getLong("id"),
                        rs.getString("name")
                )
        );
    }

    public Project add(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (project.getId() == 0) {
            jdbcTemplate.update(
                    // language=SQL
                    "INSERT INTO projects(name) VALUES (:name)",
                    new MapSqlParameterSource(Map.of(
                            "name", project.getName()
                    )),
                    keyHolder

            );
            long id = keyHolder.getKey().longValue();
            return getByID(id);
        }
        jdbcTemplate.update(
                // language=SQL
                "UPDATE  projects set name = :name where id = :id", // named parameter - вместо ? подставляем псевдонимы :content
                // map -> key, value
                // TODO:
                //  1. если content и value не могут быть null -> Map.of("content", post.getContent(), "media", post.getMedia());
                //  2. если хотя бы один может быть null - надо собирать "руками"
                Map.of(
                        "id", project.getId(),
                        "name", project.getName()
                )

        );
        return getByID(project.getId());

    }

    public List<Project> search(String text) {
        List<Project> all = getAll();
        List<Project> result = new ArrayList<>();
        for (Project project: all) {
            if (contains(text, project.getName())) {
                result.add(project);
            }
        }
        return result;
    }


    private boolean contains(String field, String target) {
        return field.toLowerCase().contains(target);
    }

    public Project deleteById(long id) {
    Project project = getByID(id);
    try {
        jdbcTemplate.update(
                // language=SQL
                "DELETE FROM projects WHERE id = :id",
                Map.of("id", project.getId())
        );
        return project;}
    catch (Exception  e) {
        throw new ConstraintException("Невозможно удалить проект", e);
    }
    }
}
