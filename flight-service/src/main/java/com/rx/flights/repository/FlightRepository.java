package com.rx.flights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rx.flights.models.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String>{
	Optional<Flight> findByFlightId(String flightId);
	
	List<Flight> findByFromLocationIgnoreCaseAndToLocationIgnoreCaseAndDateBetween(String fromLocation, String toLocation,LocalDate fromDate,LocalDate toDate);
}
