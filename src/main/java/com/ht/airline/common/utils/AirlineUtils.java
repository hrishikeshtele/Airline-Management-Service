package com.ht.airline.common.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class AirlineUtils {

  public static Timestamp getRandomTimeStamp(int futureDay) {
    Timestamp currentTs = new Timestamp(System.currentTimeMillis());
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(currentTs.getTime());
    cal.set(
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH),
        getRandomNumber(12, 24),
        0,
        0);
    cal.add(Calendar.DAY_OF_MONTH, futureDay);
    return new Timestamp(cal.getTime().getTime());
  }

  public static int getRandomNumber(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }

  public static Timestamp getFlightDuration(Timestamp currentTs, double distance) {
    double minutes = distance / 12;
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(currentTs.getTime());
    cal.add(Calendar.MINUTE, (int) minutes);
    return new Timestamp(cal.getTime().getTime());
  }

  public static double distance(Double lat1, Double lon1, Double lat2, Double lon2, char unit) {
    double theta = lon1 - lon2;
    double dist =
        Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
    if (unit == 'K') {
      dist = dist * 1.609344;
    } else if (unit == 'N') {
      dist = dist * 0.8684;
    }
    return (dist);
  }

  private static double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
  }

  private static double rad2deg(double rad) {
    return (rad * 180.0 / Math.PI);
  }

  public static String getSeatString(Integer number) {
    Integer n = 1;
    String seatString = "1";
    while (n < number) {
      n++;
      seatString += "," + n;
    }
    return seatString;
  }
}
