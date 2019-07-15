package br.edu.utfpr.pb.oo24s.aula4.javafx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataConverter {

    public static Date getStringToData(String dataTexto){
        Date data = null;
        SimpleDateFormat format = 
                new SimpleDateFormat("dd/MM/yyyy");
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public static String getDataToString(Date data){
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
}
