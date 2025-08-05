package com.nikitanevmyvaka.weatherapi.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class APIService {





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

            return arrayResult.get(0).getAsJsonObject();



        }
        catch (NullPointerException | IOException e){
            System.out.println("Wrong city, please try again");
            System.out.println();

        }
        return null;


    }

    public void displayWeatherData(double latitude, double longitude){
        try {

            String urlWeatherAddres = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,dew_point_2m";

            HttpURLConnection apiConnection = fetchApiConnection(urlWeatherAddres);
            if (apiConnection.getResponseCode()!=200){
                System.out.println("Error while connecting to API");
                return;
            }
            String apiJson= getApiJson(apiConnection);



            JsonObject weatherData = JsonParser.parseString(apiJson).getAsJsonObject();

            if (!weatherData.has("current")) {
                System.out.println("JSON does not contain 'current' key");
                return;
            }
            JsonObject currentWeather= weatherData.get("current").getAsJsonObject();

            String time= currentWeather.get("time").getAsString();
            System.out.println("Current time: "+time);

            double temperature= currentWeather.get("temperature_2m").getAsDouble();
            System.out.println("Current temperature: "+ temperature);

            long relativeHumidity = currentWeather.get("relative_humidity_2m").getAsLong();
            System.out.println("Relative Humidity: " + relativeHumidity);

            System.out.println();


        }
        catch (Exception e){
            System.out.println("Error while getting weather data "+ e.getMessage());

        }
    }




    private HttpURLConnection fetchApiConnection(String url){
        try{
            URL rl= new URL(url);
            HttpURLConnection conn= (HttpURLConnection) rl.openConnection();

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
