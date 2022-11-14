package com.rx.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatUpdateRequest {
	private List<String> seats;
	private String flightId;
	
}
