package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.net.ssl.HttpsURLConnection;

public class TimeConverter {
	
	private static final String DATE_FORMAT = "yyyy MMM dd HH:mm z";

	public String convertTime(double lat, double lg, String time)
			throws Exception {
		String ZoneId_AsString = TimezoneMapper.latLngToTimezoneString(lat, lg);
		ZoneId zoneId = ZoneId.of(ZoneId_AsString);
		LocalDateTime ldt = LocalDateTime.parse(time,
				DateTimeFormatter.ofPattern(DATE_FORMAT));
		ZonedDateTime localZonedDateTime = ldt.atZone(zoneId);
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return format.format(localZonedDateTime);
	}
}
