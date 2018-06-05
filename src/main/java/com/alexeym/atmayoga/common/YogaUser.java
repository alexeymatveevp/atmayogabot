package com.alexeym.atmayoga.common;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Alexey Matveev on 05.06.2018
 */
public class YogaUser {

    private Integer id;
    private String firstName;
    private String lastName;
    private Map<String, Practice> practiceMap = new TreeMap<>();

    public YogaUser() {
    }

    public YogaUser(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, Practice> getPracticeMap() {
        return practiceMap;
    }

    public void setPracticeMap(Map<String, Practice> practiceMap) {
        this.practiceMap = practiceMap;
    }
}
