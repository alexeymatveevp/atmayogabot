package com.alexeym.atmayoga.storage;

import com.alexeym.atmayoga.model.Chat;
import com.alexeym.atmayoga.model.ReactionMemory;
import com.alexeym.atmayoga.model.ThoughtMemory;
import com.alexeym.atmayoga.model.UserMetadata;
import com.alexeym.atmayoga.model.YogaMessage;
import com.alexeym.atmayoga.model.YogaUser;
import io.jsondb.JsonDBTemplate;

/**
 * @author Alexey Matveev on 06.06.2018
 */
public class JsonDBInit {

    public static JsonDBTemplate jsonDBTemplate;

    static {
        //Actual location on disk for database files, process should have read-write permissions to this folder
        String dbFilesLocation = "jsondb";

        //Java package name where POJO's are present
        String baseScanPackage = "com.alexeym.atmayoga.model";

        jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);

        // nit
        if (!jsonDBTemplate.collectionExists(YogaUser.class)) {
            jsonDBTemplate.createCollection(YogaUser.class);
        }
        if (!jsonDBTemplate.collectionExists(UserMetadata.class)) {
            jsonDBTemplate.createCollection(UserMetadata.class);
        }
        if (!jsonDBTemplate.collectionExists(Chat.class)) {
            jsonDBTemplate.createCollection(Chat.class);
        }
        if (!jsonDBTemplate.collectionExists(YogaMessage.class)) {
            jsonDBTemplate.createCollection(YogaMessage.class);
        }
        if (!jsonDBTemplate.collectionExists(ReactionMemory.class)) {
            jsonDBTemplate.createCollection(ReactionMemory.class);
        }
        if (!jsonDBTemplate.collectionExists(ThoughtMemory.class)) {
            jsonDBTemplate.createCollection(ThoughtMemory.class);
        }
    }
}
