package com.ht.airline.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

  public static final String COMPLETED_STATUS = "COMPLETED";
  public static final String UPCOMING_STATUS = "UPCOMING";
  public static final String CANCELLED_STATUS = "CANCELLED";
  public static final String ECONOMY_CLASS = "E";
  public static final String PREMIUM_CLASS = "P";
  public static final String DOUBLE_TWO_DECIMAL_FORMAT = "%.2f";
  public static final String COMMA_REGEX = ",";
}
