package com.onlyjoon.test;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Code, Long> {
    Optional<Code> findByCode(String code);
}
