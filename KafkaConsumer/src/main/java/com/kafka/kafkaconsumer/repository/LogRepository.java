package com.kafka.kafkaconsumer.repository;

import com.kafka.kafkaconsumer.entity.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {
}
