package com.rx.flights.converter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalTime attribute) {
		// TODO Auto-generated method stub
		return attribute == null ? null : Timestamp.valueOf(
				LocalDateTime.of(LocalDate.of(1970,1,1),attribute) //fake place holder January 1, 1970 (co-ordinated UTC)
		);
	}

	@Override
	public LocalTime convertToEntityAttribute(Timestamp dbData) {
		// TODO Auto-generated method stub
		return dbData == null ? null : dbData.toLocalDateTime().toLocalTime();
	}

}
