package com.richard.h2.resource;

import com.richard.h2.domain.TestH2;
import com.richard.h2.repositories.TestH2Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/h2")
public class H2Resource {

    private final TestH2Repository testH2Repository;

    public H2Resource(TestH2Repository testH2Repository) {
        this.testH2Repository = testH2Repository;
    }

    @PostConstruct
    public void init () {
        testH2Repository.saveAll(
            Stream.of(new TestH2(null, "Core Java"), new TestH2(null, "Spring Boot")).collect(Collectors.toList()));
    }

    @GetMapping("/tests")
    public List<TestH2> findAll() {
        return testH2Repository.findAll();
    }

}
