package com.trileuco.app.swapi.service;

import com.trileuco.app.swapi.domain.*;
import com.trileuco.app.swapi.exception.PersonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PersonInfoServiceTest {

    @Mock
    private SwapiService swapiService;

    @InjectMocks
    private PersonInfoService personInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPersonInfoByName_ReturnsPersonInfo_WhenPersonExists() {
        String testName = "Luke Skywalker";
        SwapiPerson mockSwapiPerson = createMockSwapiPerson(testName);
        when(swapiService.getPersonByName(testName)).thenReturn(mockSwapiPerson);

        SwapiPlanet mockSwapiPlanet = createMockSwapiPlanet();
        when(swapiService.getPlanetByUrl(mockSwapiPerson.getHomeworld())).thenReturn(mockSwapiPlanet);

        SwapiVehicle mockSwapiVehicle = createMockSwapiVehicle();
        when(swapiService.getVehicleByUrl(mockSwapiPerson.getVehicles().get(0))).thenReturn(mockSwapiVehicle);

        SwapiFilm mockSwapiFilm = createMockSwapiFilm();
        when(swapiService.getFilmByUrl(mockSwapiPerson.getFilms().get(0))).thenReturn(mockSwapiFilm);

        PersonInfo result = personInfoService.getPersonInfoByName(testName);

        assertEquals(testName, result.getName());
        assertEquals(mockSwapiPerson.getBirthYear(), result.getBirthYear());
        assertEquals(mockSwapiPerson.getGender(), result.getGender());
        assertEquals(mockSwapiPlanet.getName(), result.getPlanetName());
        assertEquals(mockSwapiVehicle.getName(), result.getFastestVehicleDriven());
        assertEquals(1, result.getFilms().size());
        assertEquals(mockSwapiFilm.getTitle(), result.getFilms().get(0).getName());
        assertEquals(mockSwapiFilm.getRelease_date(), result.getFilms().get(0).getReleaseDate());

        verify(swapiService, times(1)).getPersonByName(testName);
        verify(swapiService, times(1)).getPlanetByUrl(mockSwapiPerson.getHomeworld());
        verify(swapiService, times(3)).getVehicleByUrl(mockSwapiPerson.getVehicles().get(0));
        verify(swapiService, times(1)).getFilmByUrl(mockSwapiPerson.getFilms().get(0));
    }

    @Test
    void getPersonInfoByName_ThrowsPersonNotFoundException_WhenPersonDoesNotExist() {
        String testName = "Nonexistent Person";
        when(swapiService.getPersonByName(testName)).thenReturn(null);

        assertThrows(PersonNotFoundException.class, () -> {
            personInfoService.getPersonInfoByName(testName);
        });

        verify(swapiService, times(1)).getPersonByName(testName);
    }

    private SwapiPerson createMockSwapiPerson(String name) {
        SwapiPerson swapiPerson = new SwapiPerson();
        swapiPerson.setName(name);
        swapiPerson.setBirthYear("19BBY");
        swapiPerson.setGender("Male");
        swapiPerson.setHomeworld("https://swapi.dev/api/planets/1/");
        swapiPerson.setVehicles(Collections.singletonList("https://swapi.dev/api/vehicles/1/"));
        swapiPerson.setFilms(Collections.singletonList("https://swapi.dev/api/films/1/"));
        return swapiPerson;
    }

    private SwapiPlanet createMockSwapiPlanet() {
        SwapiPlanet swapiPlanet = new SwapiPlanet();
        swapiPlanet.setName("Tatooine");
        return swapiPlanet;
    }

    private SwapiVehicle createMockSwapiVehicle() {
        SwapiVehicle swapiVehicle = new SwapiVehicle();
        swapiVehicle.setName("X-34 landspeeder");
        swapiVehicle.setMax_atmosphering_speed("1000");
        return swapiVehicle;
    }

    private SwapiFilm createMockSwapiFilm() {
        SwapiFilm swapiFilm = new SwapiFilm();
        swapiFilm.setTitle("A New Hope");
        swapiFilm.setRelease_date("1977-05-25");
        return swapiFilm;
    }
}
