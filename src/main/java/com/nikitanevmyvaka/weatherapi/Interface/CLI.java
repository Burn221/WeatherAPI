package com.nikitanevmyvaka.weatherapi.Interface;

import com.google.gson.JsonObject;
import com.nikitanevmyvaka.weatherapi.Service.APIService;
import com.nikitanevmyvaka.weatherapi.dto.HistoryDTO;
import com.nikitanevmyvaka.weatherapi.repository.DatabaseHistory;

import java.util.Scanner;

public class CLI {
    APIService service= new APIService();
    DatabaseHistory dbHistory= new DatabaseHistory();
    public CLI(APIService service){
        this.service=service;
    }

    public void run() {
        Scanner scanner= new Scanner(System.in);
        do {
            try {
                System.out.println("Welcome to the Weather Api program! Enter the city to check the weather");
                String city= scanner.nextLine().trim().toLowerCase();
                if(city.equalsIgnoreCase("exit")) break;
                JsonObject cityData= service.getLocationData(city);
                double latitude= cityData.get("latitude").getAsDouble();
                double longitude= cityData.get("longitude").getAsDouble();

                HistoryDTO newDto= service.displayWeatherData(latitude,longitude, city);
                dbHistory.insertDatabaseHistory(newDto);

                System.out.println("Your current city is: "+ newDto.getCity()+"\n"+
                        "Time: "+newDto.getTime()+"\n"+
                        "Temperature: "+newDto.getTemperature()+"°C"+"\n"+
                        "Humidity: "+newDto.getRelativeHumidity()+"%"+"\n"+
                        "Wind speed: "+newDto.getWindspeed()+" km/h");
                System.out.println();


            }
            catch (Exception e){
                System.out.println();

            }


        } while (true);
    }

}
