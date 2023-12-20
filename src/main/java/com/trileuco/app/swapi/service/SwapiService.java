package com.trileuco.app.swapi.service;

import com.trileuco.app.swapi.domain.SwapiFilm;
import com.trileuco.app.swapi.domain.SwapiPerson;
import com.trileuco.app.swapi.domain.SwapiPlanet;
import com.trileuco.app.swapi.domain.SwapiVehicle;

public interface SwapiService {
    SwapiPerson getPersonByName(String name);
    SwapiPlanet getPlanetByUrl(String planetUrl);
    SwapiVehicle getVehicleByUrl(String vehicleUrl);
    SwapiFilm getFilmByUrl(String filmUrl);
}

