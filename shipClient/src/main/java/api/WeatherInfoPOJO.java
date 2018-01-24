package api;

import java.util.Date;

public class WeatherInfoPOJO {

    private Date localObservationDateTime;
    private int epochTime;
    private String WeatherText;
    private Temperature temperature;

    public Date getLocalObservationDateTime() {
        return localObservationDateTime;
    }

    public void setLocalObservationDateTime(Date localObservationDateTime) {
        this.localObservationDateTime = localObservationDateTime;
    }


    public int getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(int epochTime) {
        this.epochTime = epochTime;
    }



    public String getWeatherText() {
        return WeatherText;
    }

    public void setWeatherText(String weatherText) {
        this.WeatherText = weatherText;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "WeatherInfoPOJO{" +
                "WeatherText='" + WeatherText + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}
