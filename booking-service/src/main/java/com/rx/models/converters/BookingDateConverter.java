package com.rx.models.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
@Converter(autoApply = true)
public class BookingDateConverter implements AttributeConverter<LocalDateTime,Timestamp>{

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
		// TODO Auto-generated method stub
		return attribute == null ? null : Timestamp.valueOf(attribute);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
		// TODO Auto-generated method stub
		return dbData == null ? null : dbData.toLocalDateTime();
	}

}
