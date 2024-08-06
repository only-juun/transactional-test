package com.onlyjoon.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TestService2Test {

    @Autowired
    private TestService2 testService2;

    @Autowired
    private TestRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @Test
    void testOuterThrowCheckedException() {
        // given // when
        // RuntimeException 이 발생할 것으로 기대하고 메서드 실행
        IOException thrownException = assertThrows(IOException.class, () -> testService2.outerThrowChecked());

        // then
        assertThat(thrownException.getMessage()).isEqualTo("Checked Exception in Inner Transactional Method");

        // outerEntity 가 DB에 저장되지 않았는지 확인 (커밋 확인)
        Optional<Code> outerEntity = testRepository.findByCode("OUTER_TX_THROW_CHECK");
        assertThat(outerEntity).isPresent();

        // innerEntity 가 DB에 저장되지 않았는지 확인 (커밋 확인)
        Optional<Code> innerEntity = testRepository.findByCode("INNER_TX_THROW_CHECK");
        assertThat(innerEntity).isPresent();

        // 데이터베이스에 저장된 총 엔티티 수가 2인지 확인 (모두 커밋되었음을 확인)
        long count = testRepository.count();
        assertThat(count).isEqualTo(2);
    }

}