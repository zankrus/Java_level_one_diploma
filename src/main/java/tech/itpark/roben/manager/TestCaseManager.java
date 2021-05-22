package tech.itpark.roben.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import tech.itpark.roben.domain.TestCase;
import tech.itpark.roben.exceptions.ConstraintException;
import tech.itpark.roben.mapper.TestCaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class TestCaseManager {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TestCaseMapper mapper = new TestCaseMapper();

    public List<TestCase> getAll() {
        return jdbcTemplate.query(
                // language=SQL
                "SELECT id, suite_id,description,attached_files FROM cases",
                mapper
        );
    }

    public TestCase getByID(long id) {
        return jdbcTemplate.queryForObject("SELECT id, suite_id,description, attached_files FROM cases where id = :id",
                Map.of("id",id),mapper
        );
    }

    public List<TestCase> lastItem() {
        return jdbcTemplate.query(
                // language=SQL
                "SELECT id, suite_id,description,attached_files FROM cases  ORDER BY id DESC LIMIT 1",
                (rs, rowNum) -> new TestCase(
                        rs.getLong("id"),
                        rs.getLong("suite_id"),
                        rs.getString("description"),
                        rs.getString("attached_files")
                )
        );
    }

    public TestCase add(TestCase testCase) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (testCase.getId() == 0) {
            jdbcTemplate.update(
                    // language=SQL
                    "INSERT INTO cases(suite_id, description, attached_files) VALUES (:suite_id, :description, :attached_files)",
                    new MapSqlParameterSource(Map.of(
                            "suite_id", testCase.getSuite_id(),
                            "description", testCase.getText(),
                            "attached_files", testCase.getFilePath()
                    )),
                    keyHolder

            );
            long id = keyHolder.getKey().longValue();
            return getByID(id);
        }
        jdbcTemplate.update(
                // language=SQL
                "UPDATE  cases set suite_id = :suite_id, description = :description,attached_files =:files where id = :id",
                Map.of(
                        "suite_id", testCase.getId(),
                        "description", testCase.getText(),
                        "attached_files", testCase.getFilePath()
                )
        );
        return getByID(testCase.getId());

    }

    public List<TestCase> search(String text) {
        List<TestCase> all = getAll();
        List<TestCase> result = new ArrayList<>();
        for (TestCase testCase: all) {
            if (contains(text, testCase.getText())) {
                result.add(testCase);
            }
        }
        return result;
    }


    private boolean contains(String field, String target) {
        return field.toLowerCase().contains(target);
    }

    public TestCase deleteById(long id) {
        TestCase testCase = getByID(id);
        try {
            jdbcTemplate.update(
                    // language=SQL
                    "DELETE FROM cases WHERE id = :id",
                    Map.of("id", testCase.getId())
            );
            return testCase;}
        catch (Exception  e) {
            throw new ConstraintException("Невозможно удалить проект", e);
        }
    }
}
