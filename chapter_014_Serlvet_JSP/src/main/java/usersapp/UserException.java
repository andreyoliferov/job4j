package usersapp;

/**
 * @autor aoliferov
 * @since 20.11.2018
 */
public class UserException extends RuntimeException {
    public UserException(String message) {
        super("error: " + message);
    }
}
