/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.*;

/**
 *
 * @author chris
 */
public class DBQuery {
    private static Statement statement; // Statement reference
    // Create statement object
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }
    
    // Return statement object
    public static Statement getStatement(){
        return statement;
    }
}
