package Handlers;

public class FigureHandler {

    public String convertToIntString(String value1, String value2) {

        if (value1 == null || value1.isEmpty()) value1 = "0";
        if (value2 == null || value2.isEmpty()) value1 = "0";

        int valueInt1 = Integer.parseInt(value1);
        int valueInt2 = Integer.parseInt(value2);

        return String.valueOf(valueInt1 + valueInt2);
    }
}
