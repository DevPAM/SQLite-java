package ca.librairies.sqlite.tests;

import ca.librairies.sqlite.SQLite;
import ca.librairies.sqlite.exceptions.SQLiteArgumentNullException;
import ca.librairies.sqlite.exceptions.SQLiteFolderNotExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

/** */
public class SQLiteTest {
    @Test
    /** Test the database creation with null parameters. */
    public void TestDatabaseCreationNullArgument() {
        Assertions.assertThrows(SQLiteArgumentNullException.class, () -> {
            new SQLite(null, null);
        });
    }
    @Test
    /** Test the database creation with folder that doesn't exist. */
    public void TestDatabaseCreationFolderNotExist() {
        Assertions.assertThrows(SQLiteFolderNotExists.class, () -> {
            new SQLite("D:\\developpements\\java\\tests_unitaires\\sql", "test");
        });
    }
    @Test
    /** Test a successful database creation. */
    public void TestSuccesDatabaseCreation() throws SQLiteFolderNotExists, SQLiteArgumentNullException, SQLException {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        sqlite.execute("create table if not exists test ( id integer primary key, name varchar(10) not null );");
        File file = new File("D:\\developpements\\java\\tests_unitaires\\sqlite\\test.sqlite");
        Assertions.assertTrue(file.exists());
    }
    @Test
    /** Test the running of empty request. */
    public void TestEmptyRequest() throws SQLiteFolderNotExists, SQLiteArgumentNullException {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        Assertions.assertThrows(SQLiteArgumentNullException.class, () -> {
            sqlite.execute(null);
        });
    }
    @Test
    /** Test the data insertion. */
    public void TestDataInsertion() throws SQLiteArgumentNullException, SQLException, SQLiteFolderNotExists {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        ResultSet result = sqlite.execute("insert into test(id, name)values(1,'test1');");
        Assertions.assertTrue((result == null));
    }
    @Test
    /** Test the data reading. */
    public void TestDataReading() throws SQLiteFolderNotExists, SQLiteArgumentNullException, SQLException {
        SQLite sqlite = new SQLite("D:\\developpements\\java\\tests_unitaires\\sqlite", "test");
        ResultSet result = sqlite.execute("select count(*) as compte from test;");
        result.next();
        Assertions.assertTrue((result.getInt("compte") > 0));
    }
}