package com.alexeym.atmayoga.jsondb;

import com.alexeym.atmayoga.model.ReactionMemory;
import com.alexeym.atmayoga.storage.CommonStorage;
import org.junit.Test;

import java.util.Date;

/**
 * @author Alexey Matveev on 15.06.2018
 */
public class TestStorage {

    @Test
    public void testInsertReactionMemory() throws Exception {
        CommonStorage commonStorage = new CommonStorage();
        commonStorage.upsert(new ReactionMemory(new Date(), "ReactionMemory", "asdf", "Asdf"));
    }
}
