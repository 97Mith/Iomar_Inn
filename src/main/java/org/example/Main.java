package org.example;

import org.example.entities.PersonEntity;
import org.example.services.PersonService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<PersonEntity> searchedPeople = PersonService.getByName("Bo");

        System.out.print(searchedPeople.get(0).getName());
    }
}