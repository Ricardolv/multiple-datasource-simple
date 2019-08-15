package com.richard.postgres.resource;

import com.richard.postgres.domain.TestPostgres;
import com.richard.postgres.repositories.TestPostgresRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/postgres")
public class PostgresResource {

    private final TestPostgresRepository testPostgresRepository;

    public PostgresResource(TestPostgresRepository testPostgresRepository) {
        this.testPostgresRepository = testPostgresRepository;
    }

    @PostConstruct
    public void init () {
        testPostgresRepository.saveAll(
            Stream.of(new TestPostgres(null, "Core Java"), new TestPostgres(null, "Spring Boot")).collect(Collectors.toList()));
    }

    @GetMapping("/tests")
    public List<TestPostgres> findAll() {
        return testPostgresRepository.findAll();
    }

}
