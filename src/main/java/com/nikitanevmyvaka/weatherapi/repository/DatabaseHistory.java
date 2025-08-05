package com.nikitanevmyvaka.weatherapi.repository;

import com.nikitanevmyvaka.weatherapi.dto.HistoryDTO;

import java.sql.*;

public class DatabaseHistory {

    String url = "jdbc:postgresql://localhost:5432/WeatherAPI";
    String user = "postgres";
    String password = "1234";

    public void insertDatabaseHistory(HistoryDTO dto){
        String sql= "INSERT INTO history (city, latitude, longitude, temperature, windspeed, humidity, time)" +
                "VALUES(?,?,?,?,?,?,?)";
        try(Connection conn= DriverManager.getConnection(url,user,password)){
            try(PreparedStatement pst= conn.prepareStatement(sql)){
                pst.setString(1,dto.getCity());
                pst.setDouble(2,dto.getLatitude());
                pst.setDouble(3,dto.getLongitude());
                pst.setDouble(4,dto.getTemperature());
                pst.setDouble(5,dto.getWindspeed());
                pst.setLong(6,dto.getRelativeHumidity());

                pst.setString(7,dto.getTime());

                pst.executeUpdate();

            }

        }
        catch (SQLException e){
            System.out.println("Couldn't connect to Database "+e.getMessage());
        }
    }









}
