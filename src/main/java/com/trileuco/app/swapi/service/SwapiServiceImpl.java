package com.trileuco.app.swapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trileuco.app.swapi.domain.SwapiFilm;
import com.trileuco.app.swapi.domain.SwapiPerson;
import com.trileuco.app.swapi.domain.SwapiPlanet;
import com.trileuco.app.swapi.domain.SwapiVehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SwapiServiceImpl implements SwapiService {

    @Value("${swapi.base.url}")
    private String swapiBaseUrl;
    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(SwapiServiceImpl.class);

    public SwapiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SwapiPerson getPersonByName(String name) {
        SwapiPerson swapiPerson = null;
        try {
            URL url = new URL(swapiBaseUrl + name.replace(" ", "%20"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                JsonNode resultsNode = rootNode.get("results");

                if (resultsNode.isArray() && !resultsNode.isEmpty()) {
                    swapiPerson = objectMapper.treeToValue(resultsNode.get(0), SwapiPerson.class);
                }
            }  else {
                log.error("Error: " + responseCode);
            }
        } catch (IOException e) {
            log.error("Error: " + e);
        }
        return swapiPerson;
    }

    @Override
    public SwapiPlanet getPlanetByUrl(String planetUrl) {
        return restTemplate.getForObject(planetUrl, SwapiPlanet.class);
    }

    @Override
    public SwapiVehicle getVehicleByUrl(String vehicleUrl) {
        return restTemplate.getForObject(vehicleUrl, SwapiVehicle.class);
    }

    @Override
    public SwapiFilm getFilmByUrl(String filmUrl) {
        return restTemplate.getForObject(filmUrl, SwapiFilm.class);
    }

}

