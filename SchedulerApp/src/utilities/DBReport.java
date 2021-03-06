package utilities;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Report;
import static utilities.DBConnection.conn;

/**
 *
 * @author Christian Dye
 */
public class DBReport {

    /**
     * Sets format for visible dates and times
     */
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Sets format for visible times
     */
    public static DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Stores list of all appointments with specific format
     */
    private static ObservableList<Report> allAppointmentsReport = FXCollections.observableArrayList();

    //Get specific appointment info from system

    /**
     * Gets all appointments in a specific format of month, type, and the count of each type per month
     * @return
     */
    public static ObservableList<Report> getallAppointmentsReport() {
        try {
            // Pulling all appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT COUNT(Type), Type, MONTH(Start) "
                    + "FROM appointments GROUP BY MONTH(Start), Type;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int monthNum = rs.getInt("MONTH(Start)");
                String month = Report.getMonth(monthNum);
                Report report = new Report(
                        month,
                        rs.getString("Type"),
                        rs.getInt("COUNT(Type)"));
                allAppointmentsReport.add(report);
            }
            statement.close();
            return allAppointmentsReport;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
