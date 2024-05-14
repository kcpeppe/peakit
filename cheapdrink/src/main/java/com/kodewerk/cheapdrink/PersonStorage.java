package com.kodewerk.cheapdrink;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class PersonStorage implements PersonStore {

    private final Map<String,Person> personsByName = new ConcurrentHashMap<>();
    private final Map<Integer,Person> personsById = new ConcurrentHashMap<>();

    public synchronized Person addPerson(String firstName, String lastName) {
        Person person = new Person(firstName, lastName);
        personsByName.put(firstName +lastName, person);
        personsById.put(person.getId(), person);
        return person;
    }

    public Person findPersonById(int id) {
        return personsById.get(id);
    }

    public synchronized Person findPersonByName(String firstName, String lastName) {
        return personsByName.get( firstName + lastName);
    }

}
