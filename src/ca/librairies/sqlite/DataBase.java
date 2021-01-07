package ca.librairies.sqlite;

import ca.librairies.sqlite.sources.SQLite;
import ca.librairies.sqlite.exceptions.SQLiteFolderNotExists;
import ca.librairies.sqlite.exceptions.SQLiteArgumentNullException;

import java.io.File;

/** The SQLite database class  */
public abstract class DataBase extends SQLite {
    /** Initializes a new instance of {@link SQLite} class.
     * @param path_folder   Path to the database folder.
     * @param database_name Name of the database. */
    public DataBase(String path_folder, String database_name) throws SQLiteArgumentNullException, SQLiteFolderNotExists {
        super(path_folder, database_name);
        if(!this.databaseFileExists()) this.createTables();
    }
    /** */
    private boolean databaseFileExists() {
        File database_file = new File(this.path);
        return database_file.exists();
    }
    /** Create the database tables. */
    protected abstract void createTables();
}
