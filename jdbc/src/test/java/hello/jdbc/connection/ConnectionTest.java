package hello.jdbc.connection;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {
    @Test
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection={}, class={}",con1, con1.getClass());
        log.info("connection={}, class={}",con2, con2.getClass());

    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        //DriverManagerDataSource - 항상 새로운 커넥션을 획득
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD); // 처음 객체를 생성할 때만 파라미터를 넘김

        useDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        // 커넥션 풀링
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("myName");

        useDataSource(dataSource);
        Thread.sleep(10000);
    }


    private void useDataSource(DataSource dataSource) throws SQLException {
        // 커넥션을 획득할때는 단순히 getConnection을 호출만함
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection={}, class-{}", con1, con1.getClass());
        log.info("connection={}, class-{}", con2, con2.getClass());
    }


}
