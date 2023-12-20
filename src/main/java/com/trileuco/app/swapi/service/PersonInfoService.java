package com.trileuco.app.swapi.service;

import com.trileuco.app.swapi.domain.*;
import com.trileuco.app.swapi.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonInfoService {

    @Autowired
    private SwapiService swapiService;

    public PersonInfo getPersonInfoByName(String name) {
        SwapiPerson swapiPerson = swapiService.getPersonByName(name);

        if (swapiPerson == null) {
            throw new PersonNotFoundException("Person not found");
        }

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(swapiPerson.getName());
        personInfo.setBirthYear(swapiPerson.getBirthYear());
        personInfo.setGender(swapiPerson.getGender());

        SwapiPlanet swapiPlanet = swapiService.getPlanetByUrl(swapiPerson.getHomeworld());
        personInfo.setPlanetName(swapiPlanet.getName());

        String fastestVehicleUrl = getFastestVehicleUrl(swapiPerson);
        if(!fastestVehicleUrl.isEmpty()) {
            SwapiVehicle swapiVehicle = swapiService.getVehicleByUrl(fastestVehicleUrl);
            personInfo.setFastestVehicleDriven(swapiVehicle.getName());
        } else {
            personInfo.setFastestVehicleDriven("");
        }

        personInfo.setFilms(getFilmInfo(swapiPerson.getFilms()));

        return personInfo;
    }

    public String getFastestVehicleUrl(SwapiPerson swapiPerson) {
        List<String> vehicleUrls = swapiPerson.getVehicles();

        if (vehicleUrls.isEmpty()) {
            return "";
        }

        String fastestVehicleUrl = vehicleUrls.get(0);

        for (String vehicleUrl : vehicleUrls) {
            SwapiVehicle swapiVehicle = swapiService.getVehicleByUrl(vehicleUrl);
            SwapiVehicle fastestVehicle = swapiService.getVehicleByUrl(fastestVehicleUrl);

            if (isVehicleFaster(swapiVehicle, fastestVehicle)) {
                fastestVehicleUrl = vehicleUrl;
            }
        }

        return fastestVehicleUrl;
    }

    private boolean isVehicleFaster(SwapiVehicle vehicle1, SwapiVehicle vehicle2) {
        String maxSpeed1 = vehicle1.getMax_atmosphering_speed();
        String maxSpeed2 = vehicle2.getMax_atmosphering_speed();

        double speed1 = Double.parseDouble(maxSpeed1);
        double speed2 = Double.parseDouble(maxSpeed2);

        return speed1 > speed2;
    }

    public List<PersonInfo.Film> getFilmInfo(List<String> filmUrls) {
        List<PersonInfo.Film> films = new ArrayList<>();

        for (String filmUrl : filmUrls) {
            SwapiFilm swapiFilm = swapiService.getFilmByUrl(filmUrl);

            PersonInfo.Film film = new PersonInfo.Film();
            film.setName(swapiFilm.getTitle());
            film.setReleaseDate(swapiFilm.getRelease_date());

            films.add(film);
        }

        return films;
    }
}

