package com.rakgenius.kalaha.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoDbServiceImpl implements MongoDbService {
    @Value("${spring.data.mongodb.host:null}")
    private String databaseHost;

    @Value("${spring.data.mongodb.port:0}")
    private int port;

    @Value("${spring.data.mongodb.database:null}")
    private String databaseName;

    @Override
    public boolean isConnected() {
        if (databaseHost == null || port == 0 || databaseName == null) {
            return false;
        }
        return true;
    }
}
