package com.nikitanevmyvaka.weatherapi;

import com.nikitanevmyvaka.weatherapi.Interface.CLI;
import com.nikitanevmyvaka.weatherapi.Service.APIService;

public class Main {
    public static void main(String[] args) {
        APIService apiService= new APIService();
        CLI cli= new CLI(apiService);

        cli.run();
    }
}
