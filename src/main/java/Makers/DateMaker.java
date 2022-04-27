package Makers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateMaker {

    private Date thisFriday;
    private Date nextFriday;
    private Calendar thisFridayTemp;
    private Calendar nextFridayTemp;
    private Calendar nearestFriday;


    public String getDates() {

        Calendar c = new GregorianCalendar();

        String currentDay = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);

        if (currentDay.equalsIgnoreCase("fri")) {
            thisFridayTemp = c;
            thisFriday = thisFridayTemp.getTime();
        } else {
            thisFridayTemp = getNearestFriday(c);
            thisFriday = thisFridayTemp.getTime();
        }

        nextFridayTemp = getNearestFriday(thisFridayTemp);
        nextFriday = nextFridayTemp.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

        return sdf.format(thisFriday.getTime()) + " " + sdf.format(nextFriday.getTime()) + " " + currentDay;

    }


    public String getShShippingDate(String inputDate, int typeOfSH) {

        String[] dateElements = inputDate.split("\\/");

        int year = Integer.parseInt(dateElements[2]);
        int day = Integer.parseInt(dateElements[0]);
        int month = Integer.parseInt(dateElements[1]) - 1;

        Calendar c = new GregorianCalendar(year, month, day);

        c.add(Calendar.DAY_OF_YEAR, typeOfSH);

        Calendar dateSupposedToShip = getNearestFriday(c);

        String currentDay = new GregorianCalendar().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);

        if (currentDay.equalsIgnoreCase("fri")) {
            nearestFriday = new GregorianCalendar();

        } else {
            nearestFriday = getNearestFriday(new GregorianCalendar());
        }

        if (dateSupposedToShip.after(nearestFriday)) nearestFriday = dateSupposedToShip;
        if (dateSupposedToShip.equals(nearestFriday)) nearestFriday = dateSupposedToShip;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

        return sdf.format(nearestFriday.getTime());

    }


    public Calendar getNearestFriday(Calendar date) {

        String dayOfWeek = "";

        do {

            date.add(Calendar.DAY_OF_YEAR, 1);
            dayOfWeek = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);

        } while (!dayOfWeek.equalsIgnoreCase("fri"));

        return date;
    }

}

