package practicjdbc1.practicjdbc1.Model;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ApplicationRequest {
    private Long id;
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;
    private Course course;
}