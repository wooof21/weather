package com.weather.repos;

import com.weather.model.AuthUsers;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthRepo extends ReactiveCrudRepository<AuthUsers, Integer> {

    Mono<AuthUsers> findByUsername(String username);
}
