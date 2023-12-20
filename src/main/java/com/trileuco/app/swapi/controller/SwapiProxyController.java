package com.trileuco.app.swapi.controller;

import com.trileuco.app.swapi.domain.PersonInfo;
import com.trileuco.app.swapi.exception.PersonNotFoundException;
import com.trileuco.app.swapi.service.PersonInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swapi-proxy")
public class SwapiProxyController {

    private final PersonInfoService personInfoService;

    public SwapiProxyController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @Operation(summary = "Get person information by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved person information"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/person-info")
    public ResponseEntity<PersonInfo> getPersonInfoByName(
            @Parameter(description = "Name of the person", required = true)
            @RequestParam(name = "name") String name) {
        try {
            PersonInfo personInfo = personInfoService.getPersonInfoByName(name);
            return ResponseEntity.ok(personInfo);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
