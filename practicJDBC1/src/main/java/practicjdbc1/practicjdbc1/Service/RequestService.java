package practicjdbc1.practicjdbc1.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practicjdbc1.practicjdbc1.Model.ApplicationRequest;
import practicjdbc1.practicjdbc1.Model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestService {
    private final Connection connection;

    public boolean addRequest(ApplicationRequest r) {

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO t_spisok (id, username, commentary, phone, handled, course_id) VALUES (DEFAULT,?,?,?,DEFAULT,?);");

            stmt.setString(1, r.getUserName());
            stmt.setString(2, r.getCommentary());
            stmt.setString(3, r.getPhone());
            stmt.setLong(4, r.getCourse().getId());

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ApplicationRequest> getAllRequests() {
        List<ApplicationRequest> requestArrayList = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT r.id AS request_id, r.username, r.commentary, r.phone, r.handled, " +
                            "r.course_id AS course_id, c.id AS course_id, c.name AS course_name, " +
                            "c.description AS course_description, c.price AS course_price " +
                            "FROM t_spisok AS r " +
                            "INNER JOIN t_courses AS c ON r.course_id = c.id;"

            );

            ResultSet res = stmt.executeQuery();

            while(res.next()){
                ApplicationRequest applicationRequest = ApplicationRequest
                        .builder()
                        .id(res.getLong("request_id"))
                        .userName(res.getString("userName"))
                        .commentary(res.getString("commentary"))
                        .phone(res.getString("phone"))
                        .handled(res.getBoolean("handled"))
                        .course(
                                Course
                                        .builder()
                                        .id(res.getLong("course_id"))
                                        .name(res.getString("course_name"))
                                        .description(res.getString("course_description"))
                                        .price(res.getInt("course_price"))
                                        .build()
                        )
                        .build();

                requestArrayList.add(applicationRequest);
            }

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestArrayList;
    }

    public ApplicationRequest getRequestById(Long id) {
        ApplicationRequest applicationRequest = null;

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT r.id AS request_id, r.username, r.commentary, r.phone, r.handled, r.course_id AS course_id, c.name AS course_name, c.description AS course_description, c.price AS course_price FROM t_spisok AS r INNER JOIN t_courses AS c ON r.course_id = c.id WHERE r.id=?;");

            stmt.setLong(1, id);

            ResultSet res = stmt.executeQuery();

            if(res.next()){
                applicationRequest = ApplicationRequest
                        .builder()
                        .id(res.getLong("request_id"))
                        .userName(res.getString("userName"))
                        .commentary(res.getString("commentary"))
                        .phone(res.getString("phone"))
                        .handled(res.getBoolean("handled"))
                        .course(
                                Course
                                        .builder()
                                        .id(res.getLong("course_id"))
                                        .name(res.getString("course_name"))
                                        .description(res.getString("course_description"))
                                        .price(res.getInt("course_price"))
                                        .build()
                        )
                        .build();

                stmt.close();

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return applicationRequest;
    }

    public boolean updateRequest(ApplicationRequest r) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE t_spisok SET username=?,commentary=?,phone=?,handled=?,course_id=? WHERE id=?");
            stmt.setString(1, r.getUserName());
            stmt.setString(2, r.getCommentary());
            stmt.setString(3, r.getPhone());
            stmt.setBoolean(4, r.isHandled());
            stmt.setLong(5, r.getCourse().getId());
            stmt.setLong(6, r.getId());

            stmt.executeUpdate();

            stmt.close();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRequest(Long id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM t_spisok WHERE id = ?");

            stmt.setLong(1, id);

            stmt.executeUpdate();

            stmt.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}