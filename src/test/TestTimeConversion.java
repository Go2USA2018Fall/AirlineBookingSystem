/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import utils.TimeConverter;

/**
 * @author Yijie Yan
 *
 */
public class TestTimeConversion {
	
	private final String date = "2019-05-06 00:00";
	
	@Test
	public void testEst() {
		LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		ZonedDateTime UTCDateTime = ldt.atZone(ZoneId.of("UTC"));
		
		// latitude and longitude of boston
		String convertedTime = TimeConverter.convertTime(42.366, -71.010, UTCDateTime);
		System.out.println(convertedTime);
		assertEquals(convertedTime,"2019-05-05 20:00 EDT");
	}
	
	@Test
	public void testCst() {
		LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		ZonedDateTime UTCDateTime = ldt.atZone(ZoneId.of("UTC"));
		
		// latitude and longitude of Chicago
		String convertedTime = TimeConverter.convertTime(41.974, -87.907, UTCDateTime);
		System.out.println(convertedTime);
		assertEquals(convertedTime,"2019-05-05 19:00 CDT");
	}
	
	@Test
	public void testPDT() {
		LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		ZonedDateTime UTCDateTime = ldt.atZone(ZoneId.of("UTC"));
		
		// latitude and longitude of San francisco
		String convertedTime = TimeConverter.convertTime(37.622, -122.379, UTCDateTime);
		System.out.println(convertedTime);
		assertEquals(convertedTime,"2019-05-05 17:00 PDT");
	}

}
