/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.OCTur.control.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author OCTI01
 */
public class DateFormatter {

    public static String toYear(Date date) {
        return formatarData(date, "yyyy");
    }

    public static String toFullMonthName(Date date) {
        return formatarData(date, "MMMM");
    }

    public static String toMonthName(Date date) {
        return formatarData(date, "MMM");
    }

    public static String toDay(Date date) {
        return formatarData(date, "dd");
    }

    public static String toMonth(Date date) {
        return formatarData(date, "MM");
    }

    public static String toDate(Date date) {
        return formatarData(date, "dd/MM/yyyy");
    }

    public static Date toDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    private static String formatarData(Date date, String formato) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(formato).format(date);
    }

    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault())).toLocalDate();
    }

    public static String toHour(Date date) {
        return formatarData(date, "HH:mm:ss");
    }

}
