package utility;

import com.example.DataMapper;
import dto.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private static final String URL = "jdbc:mysql://localhost:3306/common";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static <T> List<T> selectData(String tableName, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                T object = (T) DeserializerUtil.fromResultSet(resultSet, Person.class);
                result.add(object);
            }
            System.out.println(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> boolean insertData(List<T> dataList, String tableName, DataMapper<T> mapper) {
        boolean isSuccess = false;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = generateInsertStatement(tableName, mapper);
            PreparedStatement statement = connection.prepareStatement(sql);

            for (T data : dataList) {
                mapper.mapData(statement, data);
                statement.addBatch();
            }

            int[] batchResult = statement.executeBatch();
            statement.close();

            // If no exceptions occurred, check if all statements were executed successfully
            for (int result : batchResult) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    isSuccess = false;
                    break;
                }
            }

            isSuccess = true; // All statements executed successfully
        } catch (SQLException e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }


    private static <T> String generateInsertStatement(String tableName, DataMapper<T> mapper) {
        StringBuilder columnNames = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        String[] columns = mapper.getColumnNames();
        for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                columnNames.append(", ");
                placeholders.append(", ");
            }
            columnNames.append(columns[i]);
            placeholders.append("?");
        }

        return "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + placeholders + ")";
    }

    public static Person selectPersonByName(String name) {
        Person person = null;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM person WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String personName = resultSet.getString("name");
                int age = resultSet.getInt("age");
                person = new Person(personName, age);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public static void updateAge(String name, int newAge) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE person SET age = ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newAge);
            statement.setString(2, name);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Age updated successfully for " + name);
            } else {
                System.out.println("Failed to update age for " + name);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> void deleteData(String tableName, String conditionColumn, Object conditionValue) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM " + tableName + " WHERE " + conditionColumn + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            if (conditionValue instanceof String) {
                statement.setString(1, (String) conditionValue);
            } else if (conditionValue instanceof Integer) {
                statement.setInt(1, (Integer) conditionValue);
            } else if (conditionValue instanceof Long) {
                statement.setLong(1, (Long) conditionValue);
            }
            // You can add more type checks and corresponding setXXX methods for other data types if needed.

            int rowsAffected = statement.executeUpdate();
            System.out.println("Successfully deleted " + rowsAffected + " row(s) from the table.");

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
