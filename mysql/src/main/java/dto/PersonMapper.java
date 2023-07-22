package dto;

import com.example.DataMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements DataMapper<Person> {
    @Override
    public void mapData(PreparedStatement statement, Person person) throws SQLException {
        statement.setString(1, person.getName());
        statement.setInt(2, person.getAge());
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"name", "age"};
    }

    @Override
    public String getPlaceholder() {
        return "(?, ?)";
    }

    @Override
    public Person fromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        return new Person(name, age);
    }
}
