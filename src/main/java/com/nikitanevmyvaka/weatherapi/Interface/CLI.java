package com.nikitanevmyvaka.weatherapi.Interface;

import com.google.gson.JsonObject;
import com.nikitanevmyvaka.weatherapi.Service.APIService;

import java.util.Scanner;

public class CLI {
    APIService service= new APIService();
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

                service.displayWeatherData(latitude,longitude);





            }
            catch (Exception e){
                System.out.println();

            }


        } while (true);
    }

}
