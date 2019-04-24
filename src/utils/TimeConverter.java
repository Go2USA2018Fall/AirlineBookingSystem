package utils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;



public class TimeConverter {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm z";
	
	private static final String INPUT_DATE_FORMAT = "yyyy_MM_dd HH:mm";
	/**
	 * Convert the UTC time to local time using latitude and longitude data. 
	 * 
	 * @param lat
	 * @param lg
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String convertTime(double lat, double lg,ZonedDateTime time){
		String ZoneId_AsString = TimezoneMapper.latLngToTimezoneString(lat, lg);
		ZoneId zoneId = ZoneId.of(ZoneId_AsString);
		ZonedDateTime localZonedDateTime = time.withZoneSameInstant(zoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return format.format(localZonedDateTime);
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param lat
	 * @param lg
	 * @param time
	 * @return
	 */
	public static LocalDateTime toUTCTime(double lat,double lg, String time){
		String ZoneId_AsString = TimezoneMapper.latLngToTimezoneString(lat, lg);
		ZoneId zoneId = ZoneId.of(ZoneId_AsString);
		DateTimeFormatter format = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
		LocalDateTime timeLocal = LocalDateTime.parse(time, format);
		ZonedDateTime zdtlocal = ZonedDateTime.of(timeLocal, zoneId);
		ZoneId zoneUTC = ZoneId.of("UTC");
		ZonedDateTime utczdt =  zdtlocal.withZoneSameInstant(zoneUTC);
		return utczdt.toLocalDateTime();	
	}
}
