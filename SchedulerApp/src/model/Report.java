package model;

/**
 *
 * @author Christian Dye
 */
public class Report {

    /**
     * The month
     */
    String month;

    /**
     * The type
     */
    String type;

    /**
     * The count
     */
    int count;

    /**
     *
     * @param month
     * @param type
     * @param count
     */
    public Report(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Gets the month name
     */
    private static String monthName;

    /**
     * Switches the month number to the month name
     * @param monthNum
     * @return
     */
    public static String getMonth(int monthNum) {
        switch (monthNum) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
        }
        return monthName;
    }

    /**
     * Gets the month
     * @return
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the count
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the month name
     * @return
     */
    public static String getMonthName() {
        return monthName;
    }

    /**
     * Sets the month name
     * @param monthName
     */
    public static void setMonthName(String monthName) {
        Report.monthName = monthName;
    }

}
