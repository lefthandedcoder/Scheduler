package utilities;

import java.sql.*;

/**
 *
 * @author Christian Dye
 */
public class DBQuery {

    /**
     * The statement
     */
    private static Statement statement; // Statement reference
    // Create statement object

    /**
     * Creates a statement
     * @param conn
     * @throws SQLException
     */
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    // Return statement object

    /**
     * Gets the statement
     * @return
     */
    public static Statement getStatement() {
        return statement;
    }
}
