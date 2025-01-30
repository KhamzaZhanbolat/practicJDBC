package practicjdbc1.practicjdbc1.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class DBConnection {

    private Connection connection;

    public DBConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JDBC_practic1",
                                                "postgres",
                                            "KhamzaZhan1998");
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    @Bean
    public Connection getConnection(){
        return connection;
    }

}
