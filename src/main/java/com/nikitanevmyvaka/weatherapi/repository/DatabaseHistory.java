package com.nikitanevmyvaka.weatherapi.repository;

import java.sql.*;

public class DatabaseHistory {

    String url = "jdbc:postgresql://localhost:5432/WeatherAPI";
    String user = "postgres";
    String password = "1234";

    public void insertDatabaseHistory(double latitude,double longitude,String city,String time,double temperature, long relativeHumidity, double windSpeed,double isRain){
        String sql= "INSERT INTO history (city, latitude, longitude, temperature, windspeed, humidity, rain, time)"+"" +
                "VALUES(?,?,?,?,?,?,?,?)";
        try(Connection conn= DriverManager.getConnection(url,user,password)){
            try(PreparedStatement pst= conn.prepareStatement(sql)){
                pst.setString(1,city);
                pst.setDouble(2,latitude);
                pst.setDouble(3,longitude);
                pst.setDouble(4,temperature);
                pst.setDouble(5,windSpeed);
                pst.setLong(6,relativeHumidity);
                pst.setDouble(7,isRain);
                pst.setString(8,time);

                pst.executeUpdate();

            }

        }
        catch (SQLException e){
            System.out.println("Couldn't connect to Database "+e.getMessage());
        }
    }









}
