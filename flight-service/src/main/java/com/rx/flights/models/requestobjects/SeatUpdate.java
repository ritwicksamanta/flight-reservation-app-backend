package com.rx.flights.models.requestobjects;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatUpdate {
	@NotBlank(message = "Flight Id is required")
	private String flightId;
	private List<String> seats = new ArrayList<>();
}


/**
 * 
 * 
 */