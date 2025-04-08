package util;

public class ValilateForm {
    public static Integer isInteger(String value, String path) throws Exception {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new Exception(path + " phải là số nguyên!!");
        }
    }

    public static Double isDouble(String value, String path) throws Exception {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new Exception(path + " phải là số thực!!");
        }
    }
}
