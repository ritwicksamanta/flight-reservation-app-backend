package com.rx.flights.controller;

import com.rx.flights.exception.ConstraintException;
import com.rx.flights.exception.UnauthorizedException;
import com.rx.flights.feign.SecurityFeign;
import com.rx.flights.models.Flight;
import com.rx.flights.models.requestobjects.SeatUpdate;
import com.rx.flights.service.FlightService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/flight")
@SuppressWarnings("unused")
@Slf4j
@CrossOrigin("*")
public class FlightController {
	@Autowired
	private FlightService service;
	
	@Autowired
	private SecurityFeign securityFeign;
	
	//for adding flight user must be an admin 
	@PostMapping(path = "/add")
	public EntityModel<?> addFlight(
			@Valid @RequestBody Flight flight,
			@RequestHeader(name=HttpHeaders.AUTHORIZATION) String auth) {
		try {
			securityFeign.checkIfAdmin(auth);
		}catch(Exception ex) {
			throw new UnauthorizedException("User is not authorized to do this action");
		}
		Map<String,String> result = new LinkedHashMap<>();
		
		if((service.addFlight(flight))!=null)
			result.put("message", "Flight added successfully");

		Link link = linkTo(
				methodOn(FlightController.class).getFlight(flight.getFlightId())
		).withRel("_details");


		Link allFlights = linkTo(
				methodOn(FlightController.class).getAllFlights()
		).withRel("_all");

		return EntityModel.of(result,link).add(allFlights);
	}

	@GetMapping(path = "/get/{id}")
	public ResponseEntity<Flight> getFlight(@PathVariable(name = "id") String id){
		return ResponseEntity.ok(
				service.getFlightById(id)
		);
	}

	@GetMapping(path = "/get/all")
	public ResponseEntity<List<Flight>> getAllFlights(){return ResponseEntity.ok(service.getAll());}
	
	@GetMapping(path = "/search")
	public ResponseEntity<List<Flight>> searchFlights(
				@RequestParam(name = "fromLocation") String fromLocation,
				@RequestParam(name="toLocation") String toLocation,
				@RequestParam(name = "fromDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
				@RequestParam(name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
			){
		
		
		log.info("todate: {}, fromDate: {}, constraint check : {}",toDate,fromDate,fromDate.isAfter(toDate));
		if(fromDate.isAfter(toDate)) throw new ConstraintException("toDate can't be before fromDate");
		return ResponseEntity.status(HttpStatus.OK).body(service.search(fromLocation, toLocation, fromDate, toDate));
	}
	
	
	//update and delete seat is possible only for a reserve or cancel request and request can be registered by a valid user only
	@PostMapping(path = "/reserve/update")
	public ResponseEntity<?> afterReservation(
			@Validated @RequestBody SeatUpdate request,
			@RequestHeader(name= HttpHeaders.AUTHORIZATION) String auth
			){
		validateToken(auth);
		boolean result = service.afterBook(request.getSeats(), request.getFlightId());
		return ResponseEntity.ok(result);
	}
	@PostMapping(path = "/reserve/delete")
	public ResponseEntity<?> afterCancel(
			@Validated @RequestBody SeatUpdate request,
			@RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth){
		validateToken(auth);
		return ResponseEntity.ok(
				service.afterCancel(request.getFlightId(), request.getSeats())
			);
				
	}
	
	private void validateToken(String token) {
		try {
			securityFeign.validateToken(token);
		}catch(Exception ex) {
			throw new UnauthorizedException("Access not allowed");
		}
	}
}