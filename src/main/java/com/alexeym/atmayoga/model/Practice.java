package com.alexeym.atmayoga.model;

/**
 * @author Alexey Matveev on 05.06.2018
 */
public class Practice {

    private String individual;
    private String physical;

    public Practice() {
    }

    public Practice(String individual, String physical) {
        this.individual = individual;
        this.physical = physical;
    }

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public String getPhysical() {
        return physical;
    }

    public void setPhysical(String physical) {
        this.physical = physical;
    }
}
