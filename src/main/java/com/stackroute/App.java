package com.stackroute;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        ;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("Create table if not exists Employee" +
                "(id int,firstName varchar(20),lastname varchar(20))");
        jdbcTemplate.update("insert into Employee values(?,?,?)", 1, "Hari", "Sado");
        String s = "2,Agha,Zafeer 3,Vishal,Pattnaik 4,Vineet,Agarwal 5,Anshuman,Mohapatra";
        List<Object[]> list = Arrays.asList(s.split(" ")).stream()
                .map(element -> element.split(","))
                .collect(Collectors.toList());
        for(Object[] o:list)
            System.out.println(Arrays.toString(o));
        jdbcTemplate.batchUpdate("insert into Employee values(?,?,?)", list);
        jdbcTemplate.update("Delete from Employee where id in (1,2,3,4,5)");
        List<Employee> employee=jdbcTemplate.query("Select * from Employee",new employeeMapper());

      for (Employee c: employee)
        {
            System.out.println(c);
        }

    }


}
final class employeeMapper implements RowMapper<Employee>
{

    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee=new Employee(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3));
        return employee;
    }
    }

