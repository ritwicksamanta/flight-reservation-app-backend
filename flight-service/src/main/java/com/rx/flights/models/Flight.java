package com.rx.flights.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.rx.flights.models.sequencegenerator.StringSequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight_tbl")
public class Flight /*implements Persistable<String> */{

	/*
		implemented persistable to stop jpa default behaviour of updating the entry in case
		the primary key is already available instead it will try to insert a new record
		and we can handle it in ControllerAdvice

		it has the method isNew() -> returns true in case the object is new one i.e. primary key is not available in the database
		else it will return false incase it's available already
	 */

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;

//	@Pattern(regexp = "^(FL)[\\d]{3,}$",message = "flight id not valid"
//			+ " [must start with 'FL' followed by digits]")
//	@Column(unique = true,nullable = false)
//	@NotBlank(message = "Flight Id can't be empty")

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_id")
	@GenericGenerator(
			name = "flight_id",strategy = "com.rx.flights.models.sequencegenerator.StringSequenceGenerator",
			parameters = {
					@Parameter(name= StringSequenceGenerator.VALUE_PREFIX_PARAMETER,value="FL_"),
					@Parameter(name = StringSequenceGenerator.NUMBER_FORMAT_PARAMETER, value="%03d"),
					@Parameter(name = StringSequenceGenerator.INCREMENT_PARAM,value="1")
			}
	)
	private String flightId;
	
	@NotBlank(message="Airlines name can't be blank")
	private String airlineName;
	
	@NotBlank(message = "Source Location can't be blank")
	private String fromLocation;
	
	@NotBlank(message = "Destination can't be blank")
	private String toLocation;
	
	@NotNull(message = "Gate No. can't be empty")
	private int gateNo;
	
	/*
	@NotNull(message = "Boarding Time can't be null")
	*/
	private LocalTime boardingTime;


//	@NotNull(message = "Date can't be null")
	private LocalDate date;

	@NotNull(message="Seat can't be empty")
	private int totalSeat;

	@ElementCollection //this will create a seats table with flightId as primary key and seat Number
	private List<String> seats;
   
	@PrePersist
	public void doCheck() {
		if(gateNo==0)this.gateNo = 1;
		if(boardingTime==null) this.boardingTime = LocalTime.of(8,45);
		if(date==null) this.date=LocalDate.now().plusDays(1);
		if(totalSeat==0) this.totalSeat=200;
		//forDate();

		fillSeats();
	}

	/**
	 * Returns the id of the entity.
	 *
	 * @return the id. Can be {@literal null}.

	@Override
	public String getId() {
		return this.flightId;
	}

	/**
	 * Returns if the {@code Persistable} is new or was persisted already.
	 *
	 * @return if {@literal true} the object is new.

	@Override
	public boolean isNew() {
		return true;
	}
	*/

	private void fillSeats(){
		this.seats =  IntStream.range(1,totalSeat+1).mapToObj(
				seatNo-> "S - "+seatNo
		).collect(Collectors.toList());
	}

//	public void forDate() {
//		if(date == null)
//			this.date = LocalDate.now();
//	}



}
