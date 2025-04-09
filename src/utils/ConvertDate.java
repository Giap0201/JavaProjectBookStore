package utils;

public class ConvertDate {
    public static java.sql.Date utilDateToSqlDate(java.util.Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Date(utilDate.getTime());
        }
        return null;
    }

    public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) {
        if (sqlDate != null) {
            return new java.util.Date(sqlDate.getTime());
        }
        return null;
    }

}
