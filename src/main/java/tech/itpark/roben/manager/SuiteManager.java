package tech.itpark.roben.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import tech.itpark.roben.domain.Suite;
import tech.itpark.roben.exceptions.ConstraintException;
import tech.itpark.roben.mapper.SuiteMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SuiteManager {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SuiteMapper mapper = new SuiteMapper();

    public List<Suite> getAll() {
        return jdbcTemplate.query(
                // language=SQL
                "SELECT id, name, project_id FROM suites",
                mapper
        );
    }

    public Suite getByID(long id) {
        return jdbcTemplate.queryForObject("SELECT id, name,project_id FROM suites where id = :id",
                Map.of("id",id),mapper
        );
    }

    public List<Suite> lastItem() {
        return jdbcTemplate.query(
                // language=SQL
                "SELECT id, name,project_id FROM suites  ORDER BY id DESC LIMIT 1",
                (rs, rowNum) -> new Suite(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("project_id")
                )
        );
    }

    public Suite add(Suite suite) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (suite.getId() == 0) {
            jdbcTemplate.update(
                    // language=SQL
                    "INSERT INTO suites(name,project_id) VALUES (:name,:project_id)",
                    new MapSqlParameterSource(Map.of(
                            "name", suite.getName(),
                            "project_id", suite.getProjectId()
                    )),
                    keyHolder

            );
            long id = keyHolder.getKey().longValue();
            return getByID(id);
        }
        jdbcTemplate.update(
                // language=SQL
                "UPDATE  suites set name = :name, project_id = :project_id where id = :id", // named parameter - вместо ? подставляем псевдонимы :content
                Map.of(
                        "id", suite.getId(),
                        "name", suite.getName(),
                        "project_id", suite.getProjectId()
                )

        );
        return getByID(suite.getId());

    }

    public List<Suite> search(String text) {
        List<Suite> all = getAll();
        List<Suite> result = new ArrayList<>();
        for (Suite suite: all) {
            if (contains(text, suite.getName())) {
                result.add(suite);
            }
        }
        return result;
    }


    private boolean contains(String field, String target) {
        return field.toLowerCase().contains(target.toLowerCase());
    }

    public Suite deleteById(long id) {
        Suite suite = getByID(id);
        try {
            jdbcTemplate.update(
                    // language=SQL
                    "DELETE FROM suites WHERE id = :id",
                    Map.of("id", suite.getId())
            );
            return suite;}
        catch (Exception  e) {
            throw new ConstraintException("Невозможно удалить проект", e);
        }
    }

}
