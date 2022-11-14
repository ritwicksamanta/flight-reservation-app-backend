package com.rx.flights.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.flights.exception.FlightNotFoundException;
import com.rx.flights.exception.SeatException;
import com.rx.flights.models.Flight;
import com.rx.flights.repository.FlightRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository repository;
	
	public Flight addFlight(Flight flight){
		return repository.save(flight);
	}

	public Flight getFlightById(String id){
		return repository.findByFlightId(id)
				.orElseThrow(()->new FlightNotFoundException("Flight Not Found"));
	}
	public List<Flight> getAll(){return repository.findAll();}
	
	public List<Flight> search(String fromLocation, String toLocation,LocalDate fromDate,LocalDate toDate){
		List<Flight> result = repository.findByFromLocationIgnoreCaseAndToLocationIgnoreCaseAndDateBetween(fromLocation, toLocation, fromDate, toDate);
		if(result.size()==0)
			throw new FlightNotFoundException("No Flights Available for given inputs");
		return result;
	}
	
	//after booking seats this method will be invoked to modify the seat details
	@Transactional
	public boolean afterBook(List<String>seats,String flightId) {
		Flight f = this.getFlightById(flightId);
		List<String> flightSeats = f.getSeats();
		if(flightSeats.size()<seats.size()) {
			//throw error	
			throw new SeatException("Seat not available");
		}
		seats.forEach((seat) ->{
			if(flightSeats.contains(seat)) 
				flightSeats.remove(seat);
			else {
				throw new SeatException("Not Sufficient Seats");
			}
				
		});
		f.setSeats(flightSeats);
		f.setTotalSeat(f.getSeats().size());
		repository.save(f);
		return true;
	}
	
	//This method has to be invoked in case of ticket cancellation
	@Transactional
	public boolean afterCancel(String flightId,List<String> seats) {
		Flight flight = this.getFlightById(flightId);
		List<String> availableSeats = flight.getSeats();
//		availableSeats.addAll(seats);
		seats.forEach((seat) -> {
			if(availableSeats.contains(seat))
					throw new SeatException("Duplicate seat not allowed");
			else availableSeats.add(seat);
		});
		//availableSeats.sort();
		flight.setSeats(availableSeats);
		flight.setTotalSeat(availableSeats.size());
		repository.save(flight);
		return true;
	}
}
