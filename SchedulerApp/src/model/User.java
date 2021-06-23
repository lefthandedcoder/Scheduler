package model;

/**
 *
 * @author Christian Dye
 */
public class User {

    /**
     * The user ID
     */
    private int userID;

    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * Blank user constructor
     */
    public User() {
    }

    /**
     * Gets the user ID
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
