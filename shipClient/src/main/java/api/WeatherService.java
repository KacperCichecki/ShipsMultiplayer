package api;

import config.Config;
import exceptions.ApiException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherService {

    public String getWeatherInfo() {

        StringBuilder sr = new StringBuilder();
        String path = Config.ApiPath();
        String apikey = Config.ApiApiKey();
        String address = path + apikey;

        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sr.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(sr.length() < 1){
            throw new ApiException("weather info too short: " + sr);
        }

        int start = sr.indexOf("Temperature") + 32;
        int stop = start + 7;
        String toAdjust = sr.substring(start, stop);
        int finalEnd = toAdjust.indexOf(",");

        return toAdjust.substring(0,finalEnd);
    }

}