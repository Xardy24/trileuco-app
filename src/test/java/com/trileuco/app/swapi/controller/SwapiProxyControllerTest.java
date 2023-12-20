package com.trileuco.app.swapi.controller;

import com.trileuco.app.swapi.domain.PersonInfo;
import com.trileuco.app.swapi.exception.PersonNotFoundException;
import com.trileuco.app.swapi.service.PersonInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwapiProxyControllerTest {

    @Mock
    private PersonInfoService personInfoService;

    @InjectMocks
    private SwapiProxyController swapiProxyController;

    @Test
    void getPersonInfoByName_ReturnsPersonInfo_WhenPersonExists() throws PersonNotFoundException {
        String testName = "Luke Skywalker";
        PersonInfo mockPersonInfo = new PersonInfo();
        when(personInfoService.getPersonInfoByName(testName)).thenReturn(mockPersonInfo);

        ResponseEntity<PersonInfo> responseEntity = swapiProxyController.getPersonInfoByName(testName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPersonInfo, responseEntity.getBody());
        verify(personInfoService, times(1)).getPersonInfoByName(testName);
    }

    @Test
    void getPersonInfoByName_ReturnsNotFound_WhenPersonDoesNotExist() throws PersonNotFoundException {
        String testName = "Nonexistent Person";
        when(personInfoService.getPersonInfoByName(testName)).thenThrow(new PersonNotFoundException());

        ResponseEntity<PersonInfo> responseEntity = swapiProxyController.getPersonInfoByName(testName);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(personInfoService, times(1)).getPersonInfoByName(testName);
    }
}
