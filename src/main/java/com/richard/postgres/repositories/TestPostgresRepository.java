package com.richard.postgres.repositories;

import com.richard.postgres.domain.TestPostgres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestPostgresRepository extends JpaRepository<TestPostgres, Long> {
}
