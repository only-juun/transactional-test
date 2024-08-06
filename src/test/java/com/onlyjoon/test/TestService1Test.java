package com.onlyjoon.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TestService1Test {

    @Autowired
    private TestService1 testService1;

    @Autowired
    private TestRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void testOuterThrowUncheckedException() {
        // given // when
        // RuntimeException 이 발생할 것으로 기대하고 메서드 실행
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> testService1.outerThrowUnchecked());

        // then
        assertThat(thrownException.getMessage()).isEqualTo("Unchecked Exception in Inner Transactional Method");

        // outerEntity 가 DB에 저장되지 않았는지 확인 (롤백 확인)
        Optional<Code> outerEntity = testRepository.findByCode("OUTER_TX_THROW_UNCHECK");
        assertThat(outerEntity).isNotPresent();

        // innerEntity 가 DB에 저장되지 않았는지 확인 (롤백 확인)
        Optional<Code> innerEntity = testRepository.findByCode("INNER_TX_THROW_UNCHECK");
        assertThat(innerEntity).isNotPresent();

        // 데이터베이스에 저장된 총 엔티티 수가 0인지 확인 (모두 롤백되었음을 확인)
        long count = testRepository.count();
        assertThat(count).isZero();
    }

}