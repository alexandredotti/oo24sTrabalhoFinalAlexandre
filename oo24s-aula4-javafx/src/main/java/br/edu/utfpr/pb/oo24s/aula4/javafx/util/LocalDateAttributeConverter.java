package br.edu.utfpr.pb.oo24s.aula4.javafx.util;

import java.time.LocalDate;
import java.sql.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements 
        AttributeConverter<LocalDate, Date>{

    @Override
    public Date convertToDatabaseColumn(LocalDate x) {
        return (x == null ? null : Date.valueOf(x));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date y) {
        return (y == null ? null : y.toLocalDate());
    }
    
}
