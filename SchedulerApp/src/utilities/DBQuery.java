package utilities;

import java.sql.*;

/**
 *
 * @author Christian Dye
 */
public class DBQuery {

    private static Statement statement; // Statement reference
    // Create statement object

    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    // Return statement object
    public static Statement getStatement() {
        return statement;
    }
}
