package com.rx.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rx.exception.BookingDetailsNotFoundException;
import com.rx.exception.CustomException;
import com.rx.feign.FlightServiceFeign;
import com.rx.models.Booking;
import com.rx.models.SeatUpdateRequest;
import com.rx.repositories.BookingsRepository;


@Service
public class BookingService {
	private final static Logger log = LoggerFactory.getLogger(BookingService.class);
	/**
	 * id_bookings -> cache for storing id
	 * bookings -> cache for all bookings
	 */
	
	@Autowired
	private BookingsRepository repository;
	@Autowired
	private FlightServiceFeign flightFeign;
	/**
	 * this method will evict bookings cache completely
	 * and id_bookings cache based on the id value
	 * @throws Exception 
	 */
	@Caching(
		evict = {
				@CacheEvict(cacheNames = "id_bookings", key = "#booking.customerId"),
				@CacheEvict(value="bookings",allEntries = true)
		}
	)
	/**
	 * 
	 * @param booking
	 * @return
	 */
	
	@Transactional
	public Booking add(Booking booking,String authToken){
		try {
			flightFeign.getFlight(booking.getFlightId());
			
			//if the flight is available then control comes here
			//send a seat remove request from the flights table
			
			flightFeign.updateFlightAfterBook(new SeatUpdateRequest(booking.getSeatNo(), booking.getFlightId()),authToken);
			// this will either throw a error if seat not available or update the seat and continue to the outer block to book ticket
		}catch(Exception ex) {
			log.error(ex.getMessage());
			throw new CustomException(ex.getMessage());
		}
		return repository.save(booking);
	}
	
	
	public Booking getBookingById(String booking_id) {
		return  repository.findById(booking_id).orElseThrow(
				()->new BookingDetailsNotFoundException("Invalid Booking Id"));
	}
	
	//cancel reservation
	
	@Caching(
			evict = {
					@CacheEvict(value="bookings",allEntries = true),
					@CacheEvict(value = "id_bookings", key = "#bookingDetails.customerId")
			}
			)
	@Transactional
	public String cancel(Booking bookingDetails,String authToken) {
		try {
			flightFeign.updateFlightAfterCancel(new SeatUpdateRequest(bookingDetails.getSeatNo(), bookingDetails.getFlightId()),authToken);
		}catch(Exception ex) {
			throw new CustomException(ex.getMessage());
		}
		repository.delete(bookingDetails);
		return "Reservation Cancelled Successfully!!";
	}
	
	
	public Page<Booking> getLastBookings(String customerId, Pageable page){
		return repository.findByCustomerIdOrderByBookingDateDesc(customerId, page);
	}
	
	@Cacheable(cacheNames = "bookings")
	public List<Booking> getAll(){
		return repository.findAll();
	}
	
	
	
	@Cacheable(cacheNames = "id_bookings",key = "#id")
	public List<Booking> getByCustomerId(String id){
		return repository.findByCustomerId(id);
	}
	
}
