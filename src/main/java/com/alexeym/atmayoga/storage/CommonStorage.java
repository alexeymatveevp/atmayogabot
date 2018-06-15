package com.alexeym.atmayoga.storage;

import java.util.List;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class CommonStorage {

    public <T> T findById(Object id, Class<T> clazz) {
        return JsonDBInit.jsonDBTemplate.findById(id, clazz);
    }

    public void upsert(Object model) {
        JsonDBInit.jsonDBTemplate.upsert(model);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return JsonDBInit.jsonDBTemplate.findAll(clazz);
    }

}
