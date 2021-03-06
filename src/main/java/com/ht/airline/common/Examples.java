package com.ht.airline.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Examples {

  public static final String LIST_AIRPORT_SUCCESS_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"id\": \"1\",\n"
          + "            \"city\": \"San Jose\",\n"
          + "            \"cityCode\": \"SJ\",\n"
          + "            \"airportName\": \"San Jose Airport\"\n"
          + "        },\n"
          + "        {\n"
          + "            \"id\": \"2\",\n"
          + "            \"city\": \"New York\",\n"
          + "            \"cityCode\": \"NY\",\n"
          + "            \"airportName\": \"New York Airport\"\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String SEARCH_FLIGHT_REQ =
      "{\n"
          + "    \"sourceAirportId\": \"2\",\n"
          + "    \"destinationAirportId\": \"8\",\n"
          + "    \"departureTs\": \"1637827200000\"\n"
          + "}";

  public static final String SEARCH_FLIGHT_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"id\": \"2dc49f71-24e9-4a0f-a554-8425f822c6ae\",\n"
          + "            \"sourceAirportId\": \"1\",\n"
          + "            \"destinationAirportId\": \"2\",\n"
          + "            \"airCarrierId\": \"HT AirForce\",\n"
          + "            \"departureTs\": \"2021-12-06T03:45:00.000+00:00\",\n"
          + "            \"arrivalTs\": \"2021-12-06T04:45:00.000+00:00\",\n"
          + "            \"economyPrice\": \"266.87\",\n"
          + "            \"premiumEconomyPrice\": \"346.93\",\n"
          + "            \"businessPrice\": \"453.67\",\n"
          + "            \"status\": \"UPCOMING\",\n"
          + "            \"seatAvailability\": {\n"
          + "                \"economySeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\",\n"
          + "                    \"11\",\n"
          + "                    \"12\",\n"
          + "                    \"13\",\n"
          + "                    \"14\",\n"
          + "                    \"15\",\n"
          + "                    \"16\",\n"
          + "                    \"17\",\n"
          + "                    \"18\",\n"
          + "                    \"19\",\n"
          + "                    \"20\",\n"
          + "                    \"21\",\n"
          + "                    \"22\",\n"
          + "                    \"23\",\n"
          + "                    \"24\",\n"
          + "                    \"25\",\n"
          + "                    \"26\",\n"
          + "                    \"27\",\n"
          + "                    \"28\",\n"
          + "                    \"29\",\n"
          + "                    \"30\",\n"
          + "                    \"31\",\n"
          + "                    \"32\",\n"
          + "                    \"33\",\n"
          + "                    \"34\",\n"
          + "                    \"35\",\n"
          + "                    \"36\",\n"
          + "                    \"37\",\n"
          + "                    \"38\",\n"
          + "                    \"39\",\n"
          + "                    \"40\",\n"
          + "                    \"41\",\n"
          + "                    \"42\",\n"
          + "                    \"43\",\n"
          + "                    \"44\",\n"
          + "                    \"45\",\n"
          + "                    \"46\",\n"
          + "                    \"47\",\n"
          + "                    \"48\",\n"
          + "                    \"49\",\n"
          + "                    \"50\",\n"
          + "                    \"51\",\n"
          + "                    \"52\",\n"
          + "                    \"53\",\n"
          + "                    \"54\",\n"
          + "                    \"55\",\n"
          + "                    \"56\",\n"
          + "                    \"57\",\n"
          + "                    \"58\",\n"
          + "                    \"59\",\n"
          + "                    \"60\"\n"
          + "                ],\n"
          + "                \"premiumSeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\",\n"
          + "                    \"11\",\n"
          + "                    \"12\",\n"
          + "                    \"13\",\n"
          + "                    \"14\",\n"
          + "                    \"15\",\n"
          + "                    \"16\",\n"
          + "                    \"17\",\n"
          + "                    \"18\",\n"
          + "                    \"19\",\n"
          + "                    \"20\"\n"
          + "                ],\n"
          + "                \"businessSeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\"\n"
          + "                ]\n"
          + "            }\n"
          + "        },\n"
          + "        {\n"
          + "            \"id\": \"770ef553-96a4-4811-abe9-dc8cb64bacec\",\n"
          + "            \"sourceAirportId\": \"1\",\n"
          + "            \"destinationAirportId\": \"2\",\n"
          + "            \"airCarrierId\": \"HT AirForce\",\n"
          + "            \"departureTs\": \"2021-12-06T03:45:00.000+00:00\",\n"
          + "            \"arrivalTs\": \"2021-12-06T04:45:00.000+00:00\",\n"
          + "            \"economyPrice\": \"257.87\",\n"
          + "            \"premiumEconomyPrice\": \"335.23\",\n"
          + "            \"businessPrice\": \"438.37\",\n"
          + "            \"status\": \"UPCOMING\",\n"
          + "            \"seatAvailability\": {\n"
          + "                \"economySeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\",\n"
          + "                    \"11\",\n"
          + "                    \"12\",\n"
          + "                    \"13\",\n"
          + "                    \"14\",\n"
          + "                    \"15\",\n"
          + "                    \"16\",\n"
          + "                    \"17\",\n"
          + "                    \"18\",\n"
          + "                    \"19\",\n"
          + "                    \"20\",\n"
          + "                    \"21\",\n"
          + "                    \"22\",\n"
          + "                    \"23\",\n"
          + "                    \"24\",\n"
          + "                    \"25\",\n"
          + "                    \"26\",\n"
          + "                    \"27\",\n"
          + "                    \"28\",\n"
          + "                    \"29\",\n"
          + "                    \"30\",\n"
          + "                    \"31\",\n"
          + "                    \"32\",\n"
          + "                    \"33\",\n"
          + "                    \"34\",\n"
          + "                    \"35\",\n"
          + "                    \"36\",\n"
          + "                    \"37\",\n"
          + "                    \"38\",\n"
          + "                    \"39\",\n"
          + "                    \"40\",\n"
          + "                    \"41\",\n"
          + "                    \"42\",\n"
          + "                    \"43\",\n"
          + "                    \"44\",\n"
          + "                    \"45\",\n"
          + "                    \"46\",\n"
          + "                    \"47\",\n"
          + "                    \"48\",\n"
          + "                    \"49\",\n"
          + "                    \"50\",\n"
          + "                    \"51\",\n"
          + "                    \"52\",\n"
          + "                    \"53\",\n"
          + "                    \"54\",\n"
          + "                    \"55\",\n"
          + "                    \"56\",\n"
          + "                    \"57\",\n"
          + "                    \"58\",\n"
          + "                    \"59\",\n"
          + "                    \"60\"\n"
          + "                ],\n"
          + "                \"premiumSeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\",\n"
          + "                    \"11\",\n"
          + "                    \"12\",\n"
          + "                    \"13\",\n"
          + "                    \"14\",\n"
          + "                    \"15\",\n"
          + "                    \"16\",\n"
          + "                    \"17\",\n"
          + "                    \"18\",\n"
          + "                    \"19\",\n"
          + "                    \"20\"\n"
          + "                ],\n"
          + "                \"businessSeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\"\n"
          + "                ]\n"
          + "            }\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String GENERIC_FLIGHT_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"id\": \"770ef553-96a4-4811-abe9-dc8cb64bacec\",\n"
          + "            \"sourceAirportId\": \"1\",\n"
          + "            \"destinationAirportId\": \"2\",\n"
          + "            \"airCarrierId\": \"HT AirForce\",\n"
          + "            \"departureTs\": \"2021-12-06T03:45:00.000+00:00\",\n"
          + "            \"arrivalTs\": \"2021-12-06T04:45:00.000+00:00\",\n"
          + "            \"economyPrice\": \"257.87\",\n"
          + "            \"premiumEconomyPrice\": \"335.23\",\n"
          + "            \"businessPrice\": \"438.37\",\n"
          + "            \"status\": \"UPCOMING\",\n"
          + "            \"seatAvailability\": {\n"
          + "                \"economySeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\",\n"
          + "                    \"11\",\n"
          + "                    \"12\",\n"
          + "                    \"13\",\n"
          + "                    \"14\",\n"
          + "                    \"15\",\n"
          + "                    \"16\",\n"
          + "                    \"17\",\n"
          + "                    \"18\",\n"
          + "                    \"19\",\n"
          + "                    \"20\",\n"
          + "                    \"21\",\n"
          + "                    \"22\",\n"
          + "                    \"23\",\n"
          + "                    \"24\",\n"
          + "                    \"25\",\n"
          + "                    \"26\",\n"
          + "                    \"27\",\n"
          + "                    \"28\",\n"
          + "                    \"29\",\n"
          + "                    \"30\",\n"
          + "                    \"31\",\n"
          + "                    \"32\",\n"
          + "                    \"33\",\n"
          + "                    \"34\",\n"
          + "                    \"35\",\n"
          + "                    \"36\",\n"
          + "                    \"37\",\n"
          + "                    \"38\",\n"
          + "                    \"39\",\n"
          + "                    \"40\",\n"
          + "                    \"41\",\n"
          + "                    \"42\",\n"
          + "                    \"43\",\n"
          + "                    \"44\",\n"
          + "                    \"45\",\n"
          + "                    \"46\",\n"
          + "                    \"47\",\n"
          + "                    \"48\",\n"
          + "                    \"49\",\n"
          + "                    \"50\",\n"
          + "                    \"51\",\n"
          + "                    \"52\",\n"
          + "                    \"53\",\n"
          + "                    \"54\",\n"
          + "                    \"55\",\n"
          + "                    \"56\",\n"
          + "                    \"57\",\n"
          + "                    \"58\",\n"
          + "                    \"59\",\n"
          + "                    \"60\"\n"
          + "                ],\n"
          + "                \"premiumSeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\",\n"
          + "                    \"11\",\n"
          + "                    \"12\",\n"
          + "                    \"13\",\n"
          + "                    \"14\",\n"
          + "                    \"15\",\n"
          + "                    \"16\",\n"
          + "                    \"17\",\n"
          + "                    \"18\",\n"
          + "                    \"19\",\n"
          + "                    \"20\"\n"
          + "                ],\n"
          + "                \"businessSeats\": [\n"
          + "                    \"1\",\n"
          + "                    \"2\",\n"
          + "                    \"3\",\n"
          + "                    \"4\",\n"
          + "                    \"5\",\n"
          + "                    \"6\",\n"
          + "                    \"7\",\n"
          + "                    \"8\",\n"
          + "                    \"9\",\n"
          + "                    \"10\"\n"
          + "                ]\n"
          + "            }\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String CREATE_FLIGHT_REQ =
      "{\n"
          + "    \"email\": \"john.doe@gmail.com\",\n"
          + "    \"airCarrierId\": \"1\",\n"
          + "    \"sourceAirportId\": \"1\",\n"
          + "    \"destinationAirportId\": \"2\",\n"
          + "    \"departureTs\": \"1637197977000\",\n"
          + "    \"arrivalTs\": \"16371989977000\"\n"
          + "}";

  public static final String UPDATE_FLIGHT_REQ =
      "{\n"
          + "    \"email\": \"john.doe@gmail.com\",\n"
          + "    \"flightId\": \"0a78a752-c390-40e7-ad1f-9f648dae4895\",\n"
          + "    \"airCarrierId\": \"1\",\n"
          + "    \"sourceAirportId\": \"3\",\n"
          + "    \"destinationAirportId\": \"6\"\n"
          + "}";

  public static final String DELETE_FLIGHT_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        \"Successfully deleted\"\n"
          + "    ]\n"
          + "}";
  public static final String DELETE_FLIGHT_REQ =
      "{\n"
          + "    \"email\": \"john.doe@gmail.com\",\n"
          + "    \"flightId\": \"bcbcc763-6508-4fae-94f5-02898d28a4dc\"\n"
          + "}";
  public static final String DELETE_BOOKING_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        \"Booking deleted successfully\"\n"
          + "    ]\n"
          + "}";

  public static final String DELETE_BOOKING_REQ =
      "{\n"
          + "    \"email\": \"john.doe@gmail.com\",\n"
          + "    \"bookingId\": \"05237a3f-4c2a-4681-b621-9fb7f4c79c3b\"\n"
          + "}";

  public static final String BOOK_FLIGHT_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"bookingId\": \"d06e8dbc-e465-449f-8f15-f472c5877bab\",\n"
          + "            \"message\": \"Booking successful\",\n"
          + "            \"totalAmount\": \"289.12\",\n"
          + "            \"passengers\": [\n"
          + "                {\n"
          + "                    \"id\": \"60c0b426-8c57-4b8b-a7b3-c5e2f4415af4\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"P\",\n"
          + "                    \"seatNumber\": \"3\",\n"
          + "                    \"pnr\": \"N735OV\"\n"
          + "                },\n"
          + "                {\n"
          + "                    \"id\": \"46ec989f-8295-4020-ae53-ee6daed210b1\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"P\",\n"
          + "                    \"seatNumber\": \"4\",\n"
          + "                    \"pnr\": \"W98U5L\"\n"
          + "                }\n"
          + "            ]\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String SELECT_SEAT_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"bookingId\": \"d06e8dbc-e465-449f-8f15-f472c5877bab\",\n"
          + "            \"message\": \"Booking updated successful\",\n"
          + "            \"totalAmount\": \"88.96\",\n"
          + "            \"passengers\": [\n"
          + "                {\n"
          + "                    \"id\": \"60c0b426-8c57-4b8b-a7b3-c5e2f4415af4\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"B\",\n"
          + "                    \"seatNumber\": \"3\",\n"
          + "                    \"pnr\": \"N735OV\"\n"
          + "                },\n"
          + "                {\n"
          + "                    \"id\": \"46ec989f-8295-4020-ae53-ee6daed210b1\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"B\",\n"
          + "                    \"seatNumber\": \"4\",\n"
          + "                    \"pnr\": \"W98U5L\"\n"
          + "                }\n"
          + "            ]\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String BOOK_FLIGHT_REQ =
      "{\n"
          + "    \"flightId\": \"0a78a752-c390-40e7-ad1f-9f648dae4895\",\n"
          + "    \"email\": \"john.doe@gmail.com\",\n"
          + "    \"rewards\": false,\n"
          + "    \"passengers\": [\n"
          + "        {\n"
          + "            \"firstName\": \"John\",\n"
          + "            \"lastName\": \"Doe\",\n"
          + "            \"mobileNumber\": \"+917798405060\",\n"
          + "            \"email\": \"john.doe@gmail.com\",\n"
          + "            \"seatClass\": \"E\",\n"
          + "            \"seatNumber\": \"1A\"\n"
          + "        },\n"
          + "        {\n"
          + "            \"firstName\": \"John\",\n"
          + "            \"lastName\": \"Doe\",\n"
          + "            \"mobileNumber\": \"+917798405060\",\n"
          + "            \"email\": \"john.doe@gmail.com\",\n"
          + "            \"seatClass\": \"E\",\n"
          + "            \"seatNumber\": \"2A\"\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String SELECT_SEAT_REQ =
      "{\n"
          + "  \"bookingId\": \"c0282ca5-590a-4b74-b77c-7fddea960021\",\n"
          + "  \"email\": \"john.doe@gmail.com\",\n"
          + "  \"passengers\": [\n"
          + "    {\n"
          + "      \"id\": \"35978f6b-91c9-444c-b4b3-0e31e389d964\",\n"
          + "      \"seatClass\": \"B\",\n"
          + "      \"seatNumber\": \"1\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"42a9972c-63ba-4c2f-9b68-22612584ef01\",\n"
          + "      \"seatClass\": \"B\",\n"
          + "      \"seatNumber\": \"2\"\n"
          + "    }\n"
          + "  ]\n"
          + "}";

  public static final String LIST_BOOKING_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"bookingId\": \"bc1d8ac5-1742-4de8-8cd9-17f60cb630b9\",\n"
          + "            \"flightId\": \"f98e940f-756e-4bed-9d9c-efeb1ea9e0f1\",\n"
          + "            \"sourceAirport\": \"San Francisco Airport\",\n"
          + "            \"destinationAirport\": \"Colorado Airport\",\n"
          + "            \"totalAmount\": \"289.12\",\n"
          + "            \"status\": \"UPCOMING\",\n"
          + "            \"passengers\": [\n"
          + "                {\n"
          + "                    \"id\": \"98432338-0796-42cb-9fb4-512013fdcbce\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"P\",\n"
          + "                    \"seatNumber\": \"6\",\n"
          + "                    \"pnr\": \"V46MV2\"\n"
          + "                },\n"
          + "                {\n"
          + "                    \"id\": \"4b93bec7-d036-403b-bffb-6d9814649947\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"P\",\n"
          + "                    \"seatNumber\": \"7\",\n"
          + "                    \"pnr\": \"C068FP\"\n"
          + "                }\n"
          + "            ]\n"
          + "        },\n"
          + "        {\n"
          + "            \"bookingId\": \"d06e8dbc-e465-449f-8f15-f472c5877bab\",\n"
          + "            \"flightId\": \"f98e940f-756e-4bed-9d9c-efeb1ea9e0f1\",\n"
          + "            \"sourceAirport\": \"San Francisco Airport\",\n"
          + "            \"destinationAirport\": \"Colorado Airport\",\n"
          + "            \"totalAmount\": \"289.12\",\n"
          + "            \"status\": \"CANCELLED\",\n"
          + "            \"passengers\": [\n"
          + "                {\n"
          + "                    \"id\": \"60c0b426-8c57-4b8b-a7b3-c5e2f4415af4\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"B\",\n"
          + "                    \"seatNumber\": \"3\",\n"
          + "                    \"pnr\": \"N735OV\"\n"
          + "                },\n"
          + "                {\n"
          + "                    \"id\": \"46ec989f-8295-4020-ae53-ee6daed210b1\",\n"
          + "                    \"firstName\": \"John\",\n"
          + "                    \"lastName\": \"Doe\",\n"
          + "                    \"mobileNumber\": \"+91779840506070\",\n"
          + "                    \"email\": \"john.doe@gmail.com\",\n"
          + "                    \"seatClass\": \"B\",\n"
          + "                    \"seatNumber\": \"4\",\n"
          + "                    \"pnr\": \"W98U5L\"\n"
          + "                }\n"
          + "            ]\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String LIST_BOOKING_REQ =
      "{\n" + "    \"email\": \"john.doe@gmail.com\"\n" + "}";

  public static final String GENERATE_FLIGHT_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        \"Generated Flights.\"\n"
          + "    ]\n"
          + "}";

  public static final String SIGN_UP_REQ =
      "{\n"
          + "    \"firstName\": \"John\",\n"
          + "    \"lastName\": \"Doe\",\n"
          + "    \"mobileNumber\": \"+91779840506070\",\n"
          + "    \"email\":\"john.doe@gmail.com\"\n"
          + "}";

  public static final String SIGN_UP_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"message\": \"Sign up successful\"\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String SIGN_IN_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"message\": \"Sign in successful\",\n"
          + "            \"firstName\": \"John\",\n"
          + "            \"lastName\": \"Doe\",\n"
          + "            \"rewards\": \"10.10\",\n"
          + "            \"mobileNumber\": \"+91779840506070\",\n"
          + "            \"isAdmin\": false\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String SIGN_IN_REQ =
      "{\n"
          + "    \"password\": \"gMwOZ1WhiGEeIjllqHI+NqJx+5mGNjQN/Yr0V9Z59SYY3T7K6I66n2vzw==\",\n"
          + "    \"email\":\"john.doe@gmail.com\"\n"
          + "}";

  public static final String CHANGE_PWD_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"message\": \"Password updated\"\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String CHANGE_PWD_REQ =
      "{\n"
          + "    \"oldPassword\": \"1234567\",\n"
          + "    \"newPassword\": \"512512\",\n"
          + "    \"email\":\"john.doe@gmail.com\"\n"
          + "}";

  public static final String FORGOT_PWD_RES =
      "{\n"
          + "    \"statusCode\": 200,\n"
          + "    \"data\": [\n"
          + "        {\n"
          + "            \"message\": \"Verification code sent successfully\"\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static final String FORGOT_PWD_REQ =
      "{\n" + "    \"email\":\"john.doe@gmail.com\"\n" + "}";
}
