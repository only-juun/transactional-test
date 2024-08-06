package com.onlyjoon.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TestService3Test {

    @Autowired
    private TestService3 testService3;

    @Autowired
    private TestRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void testOuterCatchUnchecked() {
        // given // when
        testService3.outerCatchUnchecked();

        // then
        // outerEntity 가 DB에 저장되었는지 확인(커밋 확인)
        Optional<Code> outerEntity = testRepository.findByCode("OUTER_NON_TX_CATCH_UNCHECK");
        assertThat(outerEntity).isPresent();

        // innerEntity 가 DB에 저장되지 않았는지 확인(커밋 확인)
        Optional<Code> innerEntity = testRepository.findByCode("INNER_TX_THROW_UNCHECK");
        assertThat(innerEntity).isPresent();

        // 데이터베이스에 저장된 총 엔티티 수가 2인지 확인 (모두 커밋되었음을 확인)
        long count = testRepository.count();
        assertThat(count).isEqualTo(2);
    }

}