package ca.librairies.sqlite.exceptions;
/*** Custom class {@link Exception} to manage when the folder not exists. */
public class SQLiteFolderNotExists extends Exception {
    /** Initializes a new instance of {@link SQLiteFolderNotExists}.
     * @param folder_path The path to the folder that doesn't exist. */
    public SQLiteFolderNotExists(String folder_path) {
        super("Le dossier '"+folder_path+"' doesn't exist.");
    }
}
