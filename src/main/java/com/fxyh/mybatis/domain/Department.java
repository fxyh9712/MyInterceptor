package com.fxyh.mybatis.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Department implements Serializable {

    private static final long serialVersionUID = 3093881573125992569L;
    private Integer id;
    private String name;
    private String location;

    private Set<Person> persons = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", persons=" + persons +
                '}';
    }
}
