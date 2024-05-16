package com.example.hawesbiya.voyageManagement.test;

import com.example.hawesbiya.voyageManagement.entities.Hebergement;
import com.example.hawesbiya.voyageManagement.entities.MoyenTransport;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONObject;



public class TestApi {
    public static void main(String[] args) throws IOException {

    String value="bej";
        URL url = new URL("https://api.geoapify.com/v1/geocode/autocomplete?text="+value+"&type=state&limit=10&apiKey=545235fb96314e3e812d8d8a04863480");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");

        int statusCode = http.getResponseCode();
        String message = http.getResponseMessage();

        System.out.println(statusCode + " " + message);

        if (statusCode == 200) { // Check for successful response (code 200)
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response:");
            System.out.println(response.toString()); // Print the JSON response

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray features = jsonResponse.getJSONArray("features");

            String[] states = new String[features.length()];
            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                String state = properties.getString("state");
                states[i] = state;
            }

// Now the states array contains only the names of the states
            for (String state : states) {
                System.out.println(state);
            }

            // Parse the JSON response to extract desired data (place this in a separate function)
            // You can use libraries like GSON or Jackson for JSON parsing
//            JSONObject jsonResponse = new JSONObject(response.toString());
//            JSONArray features = jsonResponse.getJSONArray("features");

//            String[] countryNames = new String[features.length()];
//            for (int i = 0; i < features.length(); i++) {
//                JSONObject feature = features.getJSONObject(i);
//                JSONObject properties = feature.getJSONObject("properties");
//                String countryName = properties.getString("name");
//                countryNames[i] = countryName;
//            }
//
//            // Print country names
//            for (String countryName : countryNames) {
//                System.out.println(countryName);
//            }

        } else {
            System.out.println("Error: " + message);
        }

        http.disconnect();
    }
}

