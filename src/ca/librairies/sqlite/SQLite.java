package ca.librairies.sqlite;

import ca.librairies.sqlite.exceptions.SQLiteArgumentNullException;
import ca.librairies.sqlite.exceptions.SQLiteFolderNotExists;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/** Class that manages the SQLite calls. */
public class SQLite {
    /** Path to the database folder. */
    private String path;
    /** Initializes a new instance of {@link SQLite} class.
     * @param path_folder Path to the database folder.
     * @param database_name Name of the database. */
    public SQLite(String path_folder, String database_name) throws SQLiteArgumentNullException, SQLiteFolderNotExists {
        // Check if the arguments are null or empty.
        if(path_folder == null || path_folder.isBlank() || path_folder.isEmpty() ) throw new SQLiteArgumentNullException("SQLite", "SQLite", "path_folder");
        if(database_name == null || database_name.isBlank() || database_name.isEmpty()) throw new SQLiteArgumentNullException("SQLite", "SQLite", "database_name");
        // Check if the folder exists.
        if(!this.folderExists(path_folder)) throw new SQLiteFolderNotExists(path_folder);
        // Set the SQLite database path.
        this.path = path_folder + File.separator + database_name + ".sqlite";
    }
    /** Get the database connection.
     * @return Connection The {@link Connection} to the database. */
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+this.path);
        if(connection == null) connection.getMetaData();
        return connection;
    }
    /** Check if the the folder exists.
     * @param folder_path Path to the folder to check.
     * @return <c>true</c> if the folder exists; else <c>false</c>. */
    private boolean folderExists(String folder_path){
        File folder = new File(folder_path);
        return folder.exists();
    }
    /** Check if the SQL request is 'SELECT' or other thing.
     * @param request The SQL request to check.
     * @return <c>true</c> if it's a SELECt request; <c>false</c> otherwise. */
    private boolean isSelectRequest(String request){
        return request.toLowerCase().trim().matches("^select(.*)");
    }
    /** Get a {@link ResultSet} copy.
     * @param datas The datas provide by he request.
     * @return ResultSet The {@link ResultSet}'s copy. */
    public ResultSet getDatasCopy(ResultSet datas) throws SQLException {
        CachedRowSet result = RowSetProvider.newFactory().createCachedRowSet();
        result.populate(datas);
        return result;
    }
    /** Execute a request on the database.
     * @param  request The request to execute.
     * @throws SQLiteArgumentNullException If the request is null or empty.
     * @throws SQLException If there is a SQL exception.
     * @return ResultSet The SQL request result. */
    public ResultSet execute(String request) throws SQLiteArgumentNullException, SQLException {
        // Check the preconditions.
        if(request == null || request.isBlank() || request.isEmpty()) throw new SQLiteArgumentNullException("SQLite", "execute", "request");
        // Get the SQLite database connection.
        Connection connection = this.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = null;
        // Execute the query.
        if(this.isSelectRequest(request)) result = this.getDatasCopy(statement.executeQuery(request));
        else statement.executeUpdate(request);
        // Close the database connection.
        connection.close();
        return result;
    }
}
