package practicjdbc1.practicjdbc1.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practicjdbc1.practicjdbc1.Model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseService {
    private final Connection connection;

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM t_courses");

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Course course = Course
                        .builder()
                        .id(res.getLong("id"))
                        .name(res.getString("name"))
                        .description(res.getString("description"))
                        .price(res.getInt("price"))
                        .build();

                courses.add(course);
            }

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(Long id) {
        Course course = null;

        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM t_courses WHERE id = ?");

            stmt.setLong(1, id);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                course = Course
                        .builder()
                        .id(res.getLong("id"))
                        .name(res.getString("name"))
                        .description(res.getString("description"))
                        .price(res.getInt("price"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;
    }
}