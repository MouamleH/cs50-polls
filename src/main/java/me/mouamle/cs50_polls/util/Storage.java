package me.mouamle.cs50_polls.util;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Storage {

    private static final DB db = DBMaker.fileDB("votes.bin")
            .closeOnJvmShutdown()
            .transactionEnable()
            .checksumHeaderBypass()
            .make();

    public static HTreeMap<Integer, String> loadDataMap() {
        return db.hashMap("votes_map", Serializer.INTEGER, Serializer.STRING).createOrOpen();
    }

    public static void commit() {
        db.commit();
    }

}
