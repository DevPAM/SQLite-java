package ca.librairies.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Class that translate {@link java.sql.ResultSet} row to {@link Object}.  */
public class SQLiteTranslator {
    /** Initialize a new instance of {@link SQLiteTranslator} class. */
    public SQLiteTranslator(){ }
    /** Methode that translate a {@link ResultSet} row to {T} object.
     * @param class_object The translation class.
     * @param row The row to translate to the class.
     * @return {T} The translation type. */
    private <T> T translateRow(Class<T> class_object, ResultSet row) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        // Create the method result.
        T result = class_object.getConstructor().newInstance();
        // Set the result parameters.
        Field[] fields = class_object.getDeclaredFields();
        for (Field field : fields)
            // Check if the field is public.
            if (field.canAccess(result))
                // Set the result field.
                field.set(result, row.getObject(field.getName()));
        // Return the result.
        return result;
    }
    /** Translate a {@link ResultSet} to a {@link java.util.List} of typed object.
     * @param class_object The translation class.
     * @param rows List of rows available.
     * @return ArrayList The list of datas translated in the type needed. */
    public <T> ArrayList<T> translate(Class<T> class_object, ResultSet rows) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Set the result.
        ArrayList<T> result = new ArrayList<>();
        // Translate the result rows.
        while(rows.next())
            result.add(this.translateRow(class_object, rows));
        // Return the result.
        if(result.size() == 0) return null;
        return result;
    }
}
