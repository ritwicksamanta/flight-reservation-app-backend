package com.rx.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.rx.models.Booking;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, String>{
	Page<Booking> findByCustomerIdOrderByBookingDateDesc(String customerId,Pageable page);
	List<Booking> findByCustomerId(String customerId);
}
