
package tech.itpark.roben.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suite {
    private long id;
    private String name;
    private long projectId;
}