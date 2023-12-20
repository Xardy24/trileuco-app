package com.trileuco.app.swapi.domain;

import java.util.List;

public class PersonInfo {

    private String name;
    private String birthYear;
    private String gender;
    private String planetName;
    private String fastestVehicleDriven;
    private List<Film> films;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getFastestVehicleDriven() {
        return fastestVehicleDriven;
    }

    public void setFastestVehicleDriven(String fastestVehicleDriven) {
        this.fastestVehicleDriven = fastestVehicleDriven;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public static class Film {
        private String name;
        private String releaseDate;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }
    }
}
