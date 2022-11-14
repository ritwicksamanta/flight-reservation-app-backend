package com.rx.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.rx.models.FlightDetailsResponse;
import com.rx.models.SeatUpdateRequest;

@FeignClient(name = "flight-service",url = "${flight-service.url}")
public interface FlightServiceFeign {
	@GetMapping(path = "/get/{id}")
	public ResponseEntity<FlightDetailsResponse> getFlight(@PathVariable(name = "id") String id);
	
	@PostMapping(path = "/reserve/update")
	ResponseEntity<?> updateFlightAfterBook(@RequestBody SeatUpdateRequest requestObj,@RequestHeader(name=HttpHeaders.AUTHORIZATION) String authToken);
	
	@PostMapping(path = "/reserve/delete")
	ResponseEntity<?> updateFlightAfterCancel(@RequestBody SeatUpdateRequest requestObj, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken);
}
