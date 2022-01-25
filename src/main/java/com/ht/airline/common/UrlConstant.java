package com.ht.airline.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlConstant {

  public static final String CUSTOMER_URL = "customer/v1.0";
  public static final String AIRPORT_URL = "airline/v1.0";
  public static final String SIGN_UP_URL = "/sign-up";
  public static final String SIGN_IN_URL = "/sign-in";
  public static final String CHANGE_PASSWORD_URL = "/change-password";
  public static final String FORGOT_PASSWORD_URL = "/forgot-password";
  public static final String LIST_URL = "/list-airports";
  public static final String SEARCH_URL = "/flight/search";
  public static final String FLIGHT_URL = "/flight";
  public static final String GENERATE_FLIGHT_URL = "/generate-flights";
  public static final String BOOK_URL = "/book";
  public static final String BOOK_SEAT_URL = "/book/seat";
  public static final String BOOK_LIST_URL = "/book/list";
}
