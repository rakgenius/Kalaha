package com.rakgenius.kalaha.repository;

import com.rakgenius.kalaha.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;

public interface GamesRepository extends MongoRepository<Game, Integer> {
    Game findBy_id(ObjectId id);
    Game findById(int id);
}
