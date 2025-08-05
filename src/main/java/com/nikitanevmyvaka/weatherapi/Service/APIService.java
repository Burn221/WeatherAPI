package com.nikitanevmyvaka.weatherapi.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikitanevmyvaka.weatherapi.dto.HistoryDTO;
import com.nikitanevmyvaka.weatherapi.repository.DatabaseHistory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class APIService {

    DatabaseHistory history= new DatabaseHistory();





    public JsonObject getLocationData(String city){
        city= city.replaceAll(" ","");
        String urlCityAddres= "https://geocoding-api.open-meteo.com/v1/search?name="+city+"&count=1&language=en&format=json";

        try{
            HttpURLConnection apiConnection= fetchApiConnection(urlCityAddres);

            if(apiConnection.getResponseCode()!=200 ){
                System.out.println("Couldn't connect API, please try again");
                System.out.println();
                return null;
            }

            String jsonResponse= getApiJson(apiConnection);

            JsonObject jsonWeather= JsonParser.parseString(jsonResponse).getAsJsonObject();

            JsonArray arrayResult= jsonWeather.getAsJsonArray("results");
            if(arrayResult==null || arrayResult.isEmpty()){
                System.out.println("No information about this city");
                return null;
            }

            return arrayResult.get(0).getAsJsonObject();



        }
        catch (NullPointerException | IOException e){
            System.out.println("Wrong city, please try again");
            System.out.println();

        }
        return null;


    }

    public HistoryDTO displayWeatherData(double latitude, double longitude, String city){
        try {

            String urlWeatherAddres = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current=temperature_2m,relative_humidity_2m,wind_speed_10m,wind_direction_10m,is_day,rain";

            HttpURLConnection apiConnection = fetchApiConnection(urlWeatherAddres);
            if (apiConnection.getResponseCode()!=200 ){
                System.out.println("Error while connecting to API");
                return null;
            }
            String apiJson= getApiJson(apiConnection);



            JsonObject weatherData = JsonParser.parseString(apiJson).getAsJsonObject();


            JsonObject currentWeather= weatherData.get("current").getAsJsonObject();



            String time= currentWeather.get("time").getAsString();


            double temperature= currentWeather.get("temperature_2m").getAsDouble();


            long relativeHumidity = currentWeather.get("relative_humidity_2m").getAsLong();


            double windSpeed= currentWeather.get("wind_speed_10m").getAsDouble();


            String windDirection= currentWeather.get("wind_direction_10m").getAsString();

            return new HistoryDTO(city,latitude,longitude, time, temperature,relativeHumidity,windSpeed);



        }
        catch (Exception e){
            System.out.println("Error while getting weather data "+ e.getMessage());
            return null;

        }
    }




    private HttpURLConnection fetchApiConnection(String url){
        try{
            URL rl= new URL(url);
            HttpURLConnection conn= (HttpURLConnection) rl.openConnection();

            if(conn==null ){
                throw new NullPointerException("Error while fetching API URL");
            }

            conn.setRequestMethod("GET");

            return conn;


        }
        catch (Exception e){
            System.out.println("Error while fetching api" + e.getMessage());
            return null;

        }
    }

    private String getApiJson(HttpURLConnection apiConnection){
        StringBuilder resultJson= new StringBuilder();

        try( Scanner scanner= new Scanner(apiConnection.getInputStream())){
            while (scanner.hasNext()){
                resultJson.append(scanner.nextLine());
            }

        }
        catch (Exception e){
            System.out.println("Error while reading the API response");
            return null;
        }

        return resultJson.toString();


    }




}
