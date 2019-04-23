package utils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;



public class TimeConverter {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm z";
	/**
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
		LocalDateTime ldt = time.toLocalDateTime();
		ZonedDateTime localZonedDateTime = ldt.atZone(zoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return format.format(localZonedDateTime);
	}
}
