package tech.itpark.roben.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    private long id;
    private long suite_id;
    private String text;
    private String filePath;
}
