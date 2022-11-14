package com.rx.models;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDetailsResponse {
	private String airlineName;
	private String fromLocation;
	private String toLocation;
	private int gateNo;
	private LocalTime boardingTime;
	private LocalDate date;
}
