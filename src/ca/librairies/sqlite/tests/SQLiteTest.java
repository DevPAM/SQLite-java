package ca.librairies.sqlite.tests;

import ca.librairies.sqlite.SQLite;
import ca.librairies.sqlite.exceptions.SQLiteArgumentNullException;
import ca.librairies.sqlite.exceptions.SQLiteFolderNotExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** */
public class SQLiteTest {
    @Test
    public void TestDatabaseCreationNullArgument() {
        Assertions.assertThrows(SQLiteArgumentNullException.class, () -> new SQLite(null, null));
    }
    @Test
    public void TestDatabaseCreationFolderNotExist() {
        Assertions.assertThrows(SQLiteFolderNotExists.class, () -> new SQLite("D:\\developpements\\java\\tests_unitaires\\sql", "test"));
    }
    @Test
    public void TestSuccesDatabaseCreation() throws SQLiteFolderNotExists, SQLiteArgumentNullException, SQLException {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        sqlite.execute("create table if not exists test ( id integer primary key, name varchar(10) not null );");
        File file = new File("D:\\developpements\\java\\tests_unitaires\\sqlite\\test.sqlite");
        Assertions.assertTrue(file.exists());
    }
    @Test
    public void TestEmptyRequest() throws SQLiteFolderNotExists, SQLiteArgumentNullException {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        Assertions.assertThrows(SQLiteArgumentNullException.class, () -> sqlite.execute(null));
    }
    @Test
    public void TestDataInsertion() throws SQLiteArgumentNullException, SQLException, SQLiteFolderNotExists {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        ResultSet result = sqlite.execute("insert into test(id, name)values(1,'test1');");
        Assertions.assertTrue((result == null));
    }
    @Test
    public void TestDataReading1() throws SQLiteFolderNotExists, SQLiteArgumentNullException, SQLException {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        ResultSet result = sqlite.execute("select count(*) as compte from test;");
        result.next();
        Assertions.assertTrue((result.getInt("compte") > 0));
    }
    @Test
    public void TestDataReading2() throws SQLiteFolderNotExists, SQLiteArgumentNullException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        ArrayList<SQLiteTestModel> result = sqlite.execute(SQLiteTestModel.class, "select id as id, name as name from test;");
        for(SQLiteTestModel model : result)
            System.out.println("ID : "+ model.id+" NAME : "+model.name);
        Assertions.assertTrue( (result.get(0).id == 1 && result.get(0).name.equals("test1")) );
    }
}