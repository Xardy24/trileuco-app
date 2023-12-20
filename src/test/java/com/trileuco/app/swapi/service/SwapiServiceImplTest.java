package com.trileuco.app.swapi.service;

import com.trileuco.app.swapi.domain.SwapiFilm;
import com.trileuco.app.swapi.domain.SwapiPerson;
import com.trileuco.app.swapi.domain.SwapiPlanet;
import com.trileuco.app.swapi.domain.SwapiVehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SwapiServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SwapiServiceImpl swapiService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);

        Field swapiBaseUrlField = SwapiServiceImpl.class.getDeclaredField("swapiBaseUrl");
        swapiBaseUrlField.setAccessible(true);
        swapiBaseUrlField.set(swapiService, "https://swapi.dev/api/people/?search=");

    }

    @Test
    public void getPersonByName_ReturnsSwapiPerson_WhenRequestIsSuccessful() {
        String mockResponse = "{\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"name\": \"Luke Skywalker\",\n" +
                "      \"height\": \"172\",\n" +
                "      \"mass\": \"77\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        when(restTemplate.getForObject(any(String.class), any())).thenReturn(mockResponse);

        SwapiPerson swapiPerson = swapiService.getPersonByName("Luke Skywalker");

        assertEquals("Luke Skywalker", swapiPerson.getName());
        assertEquals("172", swapiPerson.getHeight());
        assertEquals("77", swapiPerson.getMass());
    }

    @Test
    public void testGetPersonByName_HttpError() {
        when(restTemplate.getForObject(any(String.class), any())).thenReturn(null);

        SwapiPerson swapiPerson = swapiService.getPersonByName("Any Name");

        Assertions.assertNull(swapiPerson);
    }

    @Test
    void getPlanetByUrl_ReturnsSwapiPlanet_WhenRequestIsSuccessful() {
        SwapiPlanet expectedPlanet = new SwapiPlanet();
        when(restTemplate.getForObject(anyString(), eq(SwapiPlanet.class))).thenReturn(expectedPlanet);

        SwapiPlanet actualPlanet = swapiService.getPlanetByUrl("https://swapi.dev/api/planets/1/");

        assertEquals(expectedPlanet, actualPlanet);
    }

    @Test
    public void getPlanetByUrl_Exception() {
        when(restTemplate.getForObject(any(String.class), eq(SwapiPlanet.class)))
                .thenThrow(new RuntimeException("Simulated RuntimeException"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> swapiService.getPlanetByUrl("https://example.com/planets/1"));

        assertEquals("Simulated RuntimeException", exception.getMessage());
    }

    @Test
    void getVehicleByUrl_ReturnsSwapiVehicle_WhenRequestIsSuccessful() {
        SwapiVehicle expectedVehicle = new SwapiVehicle();
        when(restTemplate.getForObject(anyString(), eq(SwapiVehicle.class))).thenReturn(expectedVehicle);

        SwapiVehicle actualVehicle = swapiService.getVehicleByUrl("https://swapi.dev/api/vehicles/1/");

        assertEquals(expectedVehicle, actualVehicle);
    }

    @Test
    public void testGetVehicleByUrl_Exception() {
        when(restTemplate.getForObject(any(String.class), eq(SwapiVehicle.class)))
                .thenThrow(new RuntimeException(new IOException("Simulated IOException")));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> swapiService.getVehicleByUrl("https://example.com/vehicles/1"));

        assertEquals("Simulated IOException", exception.getCause().getMessage());
    }

    @Test
    void getFilmByUrl_ReturnsSwapiFilm_WhenRequestIsSuccessful() {
        SwapiFilm expectedFilm = new SwapiFilm();
        when(restTemplate.getForObject(anyString(), eq(SwapiFilm.class))).thenReturn(expectedFilm);

        SwapiFilm actualFilm = swapiService.getFilmByUrl("https://swapi.dev/api/films/1/");

        assertEquals(expectedFilm, actualFilm);
    }

    @Test
    public void testGetFilmByUrl_Exception() {
        when(restTemplate.getForObject(any(String.class), eq(SwapiFilm.class)))
                .thenThrow(new RuntimeException("Simulated RuntimeException"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> swapiService.getFilmByUrl("https://example.com/films/1"));

        assertEquals("Simulated RuntimeException", exception.getMessage());
    }
}
