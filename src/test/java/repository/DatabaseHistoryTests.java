package repository;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class DatabaseHistoryTests {

    @Test
    void dbConnectionTest() {


        String url = "jdbc:postgresql://localhost:5432/WeatherAPI";
        String user = "postgres";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url,user,password)) {
            System.out.println("Successfully connected to a database");
            try(Statement st= conn.createStatement()){
                ResultSet rs= st.executeQuery("SELECT * FROM public.history");

                while(rs.next()){
                    int id= rs.getInt("id");
                    String city= rs.getString("City");

                    System.out.println(id+" "+ city);
                }
            }




        }
        catch (SQLException e){

        }


    }
}

