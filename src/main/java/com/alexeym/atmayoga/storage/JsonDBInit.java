package com.alexeym.atmayoga.storage;

import com.alexeym.atmayoga.common.UserMetadata;
import com.alexeym.atmayoga.common.YogaUser;
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
        String baseScanPackage = "com.alexeym.atmayoga.common";

        jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);

        // init
        if (!jsonDBTemplate.collectionExists(YogaUser.class)) {
            jsonDBTemplate.createCollection(YogaUser.class);
        }
        if (!jsonDBTemplate.collectionExists(UserMetadata.class)) {
            jsonDBTemplate.createCollection(UserMetadata.class);
        }
    }
}
