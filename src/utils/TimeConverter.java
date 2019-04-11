package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TimeConverter {
	
	private final String USER_AGENT = "Mozilla/5.0";
	
    public void convertTime(double lat,double lg, long timeinMillis) throws Exception {
    	
        String url = "https://maps.googleapis.com/maps/api/timezone/json?location="+lat+","+lg+"&timestamp="+timeinMillis+"&key=AIzaSyASyfTX0fkywFDL1bnriEP0j9BDCmziNTQ";

        URL obj = new URL(url);
        HttpsURLConnection  con = (HttpsURLConnection ) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}
