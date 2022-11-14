package com.rx.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rx.models.generators.CustomSequenceIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "bookings_tbl")
public class Booking {
	@Id
	//hibernate's sequencestylegenerator will be used to auto-generate custom Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "string_seq")
	@GenericGenerator(name = "string_seq",parameters = {
			@Parameter(name=CustomSequenceIdGenerator.INCREMENT_PARAM, value="10"),
			@Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER,value = "%d"),
			@Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "BK_")
	}, strategy = "com.rx.models.generators.CustomSequenceIdGenerator")
	private String bookingId;
	
	//@NotNull(message = "Seat Number can't be blank")
	@ElementCollection
//	@JsonIgnore
	
	private List<String> seatNo;
	
	@NotBlank(message = "Customer Id can't be blank")
	private String customerId;
	
	@NotBlank(message = "Flight Id is required")
	private String flightId;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime bookingDate;
	
	@PrePersist
	public void beforePersist() {
		if(this.bookingDate == null)
			this.bookingDate = LocalDateTime.now();
	}
}
