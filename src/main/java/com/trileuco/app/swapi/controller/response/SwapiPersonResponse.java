package com.trileuco.app.swapi.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trileuco.app.swapi.domain.SwapiPerson;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiPersonResponse {

    private List<SwapiPerson> results;


    public List<SwapiPerson> getResults() {
        return results;
    }

    public void setResults(List<SwapiPerson> results) {
        this.results = results;
    }
}
