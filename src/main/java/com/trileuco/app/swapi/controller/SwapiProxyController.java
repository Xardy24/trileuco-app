package com.trileuco.app.swapi.controller;

import com.trileuco.app.swapi.domain.PersonInfo;
import com.trileuco.app.swapi.exception.PersonNotFoundException;
import com.trileuco.app.swapi.service.PersonInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swapi-proxy")
public class SwapiProxyController {

    private final PersonInfoService personInfoService;

    public SwapiProxyController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @GetMapping("/person-info")
    public ResponseEntity<PersonInfo> getPersonInfoByName(@RequestParam(name = "name") String name) {
        try {
            PersonInfo personInfo = personInfoService.getPersonInfoByName(name);
            return ResponseEntity.ok(personInfo);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

