package com.rakgenius.kalaha.service;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDbSettings {
    @Bean
    public MongoClientOptions mongoOptions() {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
        optionsBuilder.serverSelectionTimeout(2000);
        optionsBuilder.connectTimeout(2000);
        return optionsBuilder.socketTimeout(2000).build();
    }
}
