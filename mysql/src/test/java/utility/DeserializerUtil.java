package utility;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeserializerUtil {
    public static <T> T fromResultSet(ResultSet resultSet, Class<T> clazz) throws SQLException {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String columnName = field.getName();
                Object value = resultSet.getObject(columnName);

                // Check if the value is null and the field type is a primitive
                if (value == null && field.getType().isPrimitive()) {
                    continue; // Skip setting null value to primitive field
                }

                field.set(object, value);
            }

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
