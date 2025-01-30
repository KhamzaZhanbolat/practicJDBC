package practicjdbc1.practicjdbc1.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {
    private Long id;
    private String name;
    private String description;
    private int price;
}

