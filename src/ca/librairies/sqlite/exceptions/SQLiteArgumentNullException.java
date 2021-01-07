package ca.librairies.sqlite.exceptions;

/** Custom class {@link Exception} to manage null argument. */
public class SQLiteArgumentNullException extends Exception {
    /** Initializes a new instance of {@link SQLiteArgumentNullException}.
     * @param type The class name.
     * @param function The function name.
     * @param  argument The argument name. */
    public SQLiteArgumentNullException(String type, String function, String argument) {
        super("L'argument '"+ argument +"' de la fonction '"+ function +"' de la classe '"+ argument + "' ne peut être null ou vide.");
    }
}
