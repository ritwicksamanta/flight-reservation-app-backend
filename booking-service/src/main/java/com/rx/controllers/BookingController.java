package com.rx.controllers;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rx.exception.UnauthorizedException;
import com.rx.feign.FlightServiceFeign;
import com.rx.feign.SecurityFeign;
import com.rx.models.Booking;
import com.rx.models.BookingResponse;
import com.rx.models.FlightDetailsResponse;
import com.rx.models.StatsModel;
import com.rx.services.BookingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/reservations")
@Slf4j
@CrossOrigin("*")
public class BookingController {
	
	@Autowired
	private BookingService service;
	 
	@Autowired
	private FlightServiceFeign flightServiceFeign;
	
	@Autowired
	private SecurityFeign securityFeign;
	
	// implemented completely
	@PostMapping(path = "/reserve")
	private ResponseEntity<?> addreservation(@Valid @RequestBody(required = true) final Booking details,
			@RequestHeader(name=HttpHeaders.AUTHORIZATION) String auth){
		if(validateTokenWithUserName(details.getCustomerId(), auth))
			return ResponseEntity.status(HttpStatus.CREATED).body(service.add(details,auth));
		else throw new UnauthorizedException("customer id not matched");
	}
	 
	// implemented completely
	@GetMapping(path="/get/customer")
	private ResponseEntity<?> getLastThreeBooking(
			@RequestParam(name = "id",required = true ) String id,
			@RequestParam(name = "page",defaultValue = "0") int page,
			@RequestParam(name = "size",defaultValue = "3") int pageSize,
			@RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth
			){
		if(validateTokenWithUserName(id, auth)) {
			
			Pageable paging  = PageRequest.of(page, pageSize);
			Page<BookingResponse> resultPage=service.getLastBookings(id, paging).map((booking) -> {
				FlightDetailsResponse flightDetailsResponse = flightServiceFeign.getFlight(booking.getFlightId()).getBody();
				return new BookingResponse(booking, flightDetailsResponse);
			});
			
			return ResponseEntity.status(200).body(resultPage);
		}
		throw new UnauthorizedException("id not matched!");
	}
	
	//implemented completely
	@GetMapping(path = "/get/customer/all")
	private ResponseEntity<?> getAllBookingsByCustomer( @RequestParam(name = "id",required = true) String id,
			@RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth){
		if(validateTokenWithUserName(id, auth)) {
			
			List<Booking> bookings = service.getByCustomerId(id);
			
			
			List<BookingResponse> response = bookings.stream().map((booking) -> {
				FlightDetailsResponse flightDetailsResponse = flightServiceFeign.getFlight(booking.getFlightId()).getBody();
				return new BookingResponse(booking, flightDetailsResponse);
			}).collect(Collectors.toList());
			
			return ResponseEntity.status(HttpStatus.OK).
					body(response);
		}else throw new UnauthorizedException("id not matched");
		
	}
	
	//cancel flight implementation
	//cancel by booking_id
	@DeleteMapping(path = "/cancel/booking")
	public ResponseEntity<?> cancelBooking(@RequestParam(name = "id",required=true) String booking_id,
			@RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth){
		
		Booking booking = service.getBookingById(booking_id);
		if(validateTokenWithUserName(booking.getCustomerId(),auth)) {
			Map<String, String> result = new LinkedHashMap<>();
			result.put("message",service.cancel(booking,auth));
			return ResponseEntity.ok(result);
		}else throw new UnauthorizedException("Customer id not matched");
		
	}
	
	
	//partially implemented need to reanalyze
	//all bookings is visible to admin only -> not implemented
	@GetMapping(path = "/get/all")
	private ResponseEntity<?> getAllBookings(@RequestHeader(name = HttpHeaders.AUTHORIZATION , required = true) String Auth){
		checkIfAdmin(Auth);
		return ResponseEntity.status(HttpStatus.OK).
				body(service.getAll());
	}
	
	//get chart data -> implementation left
	@GetMapping(path = "/get/stats")
	public ResponseEntity<?> getDataForChart(@RequestHeader(name = "Authorization") String auth,
			@RequestParam(name = "id") String id){
		Map<String,Integer> statsMap = new HashMap<>();
		List<Booking> bookings = null;
		try{
			securityFeign.validateToken(auth);
			try {
				if(securityFeign.checkRole(auth)) {
					//if admin it will return true
					bookings = service.getAll();
				}else {
					//for other user
					bookings = service.getByCustomerId(id);
				}
			}catch(Exception ex) {
				
			}
		}catch(Exception ex) {
			throw new UnauthorizedException();
		}
		if(bookings!=null && bookings.size()>0)
			statsMap = populateStatsMap(bookings);
		else {
			for(Month month:Month.values()) {
				//log.info("{}",month);
				statsMap.put(month.name(), 0);
			}
		}
		log.info("{}",statsMap);
		List<StatsModel> resultList = new ArrayList<>();
		for(Entry<String, Integer> entry:statsMap.entrySet())
			resultList.add(new StatsModel(entry.getKey(), entry.getValue()));
		return ResponseEntity.ok(resultList);
	}
	
	
	private Map<String, Integer> populateStatsMap(List<Booking> bookings){
		Map<String, Integer> statsMap = new HashMap<>();
		bookings.stream().forEach(booking -> {
			Stream.of(Month.values()).forEach(
					(month) -> {
						if(booking.getBookingDate().getMonth()==month)
							statsMap.put(month.name(), statsMap.getOrDefault(month.name(), 0)+1);
						else statsMap.putIfAbsent(month.name(), 0);
					}
			);
		});
		return statsMap;
	}
	
	
	private void checkIfAdmin(String auth) {
		try {
			securityFeign.checkRole(auth);
		}catch(Exception ex) {
			throw new UnauthorizedException("User not authorized");
		}
	}
	
	private void validateToken(String auth) {
		try {
			securityFeign.validateToken(auth);
		}catch(Exception ex) {
			throw new UnauthorizedException();
		}
	}
	
	private boolean validateTokenWithUserName(String userName,String auth) {
		try {
			return securityFeign.doValidateUsername(userName, auth);
		}catch(Exception ex){
			throw new UnauthorizedException();
		}
	}
}
