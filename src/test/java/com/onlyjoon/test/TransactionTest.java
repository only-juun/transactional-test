package com.onlyjoon.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionTest {

    @Autowired
    private TestService testService;

    @Autowired
    private TestRepository testRepository;

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("outerTxTestParameters")
    void testOuterTransactionalMethod(boolean outerThrow, boolean outerChecked, boolean innerTransactional, boolean innerThrow, boolean innerChecked, int expectedCount) {
        try {
            testService.outerTransactionalMethod(outerThrow, outerChecked, innerTransactional, innerThrow, innerChecked);
        } catch (IOException | RuntimeException e) {
            // 예외를 무시하거나 로깅
        }

        // DB에 저장된 엔티티 수 확인
        assertEquals(expectedCount, testRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("outerNonTxTestParameters")
    void testOuterNonTransactionalMethod(boolean outerThrow, boolean outerChecked, boolean innerTransactional, boolean innerThrow, boolean innerChecked, int expectedCount) {
        try {
            testService.outerNonTransactionalMethod(outerThrow, outerChecked, innerTransactional, innerThrow, innerChecked);
        } catch (IOException | RuntimeException e) {
            // 예외를 무시하거나 로깅
        }

        // DB에 저장된 엔티티 수 확인
        assertEquals(expectedCount, testRepository.findAll().size());
    }

    private static Stream<Arguments> outerTxTestParameters() {
        return Stream.of(
                // outerThrow, outerChecked, innerTransactional, innerThrow, innerChecked, expectedCount
                Arguments.of(true, true, true, true, true, 2),   // 모든 예외 발생, 체크 예외, 트랜잭션
                Arguments.of(true, true, true, true, false, 0),  // 모든 예외 발생, 체크 예외, 트랜잭션
                Arguments.of(true, true, true, false, true, 2),  // 외부 체크 예외, 내부 정상
                Arguments.of(true, true, true, false, false, 2), // 외부 체크 예외, 내부 정상
                Arguments.of(true, true, false, true, true, 2),  // 외부 체크 예외, 내부 비트랜잭션
                Arguments.of(true, true, false, true, false, 0), // 외부 체크 예외, 내부 비트랜잭션
                Arguments.of(true, true, false, false, true, 2), // 외부 체크 예외, 내부 비트랜잭션, 내부 정상
                Arguments.of(true, true, false, false, false, 2),// 외부 체크 예외, 내부 비트랜잭션, 내부 정상
                Arguments.of(true, false, true, true, true, 2),  // 외부 언체크 예외, 내부 트랜잭션, 체크 예외
                Arguments.of(true, false, true, true, false, 0), // 외부 언체크 예외, 내부 트랜잭션, 언체크 예외
                Arguments.of(true, false, true, false, true, 0), // 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(true, false, true, false, false, 0),// 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(true, false, false, true, true, 2), // 외부 언체크 예외, 내부 비트랜잭션, 체크 예외
                Arguments.of(true, false, false, true, false, 0),// 외부 언체크 예외, 내부 비트랜잭션, 언체크 예외
                Arguments.of(true, false, false, false, true, 0),// 외부 언체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(true, false, false, false, false, 0),// 외부 언체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, true, true, true, true, 2),   // 외부 체크 예외, 내부 트랜잭션, 체크 예외
                Arguments.of(false, true, true, true, false, 0),  // 외부 체크 예외, 내부 트랜잭션, 언체크 예외
                Arguments.of(false, true, true, false, true, 2),  // 외부 체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, true, true, false, false, 2), // 외부 체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, true, false, true, true, 2),  // 외부 체크 예외, 내부 비트랜잭션, 체크 예외
                Arguments.of(false, true, false, true, false, 0), // 외부 체크 예외, 내부 비트랜잭션, 언체크 예외
                Arguments.of(false, true, false, false, true, 2), // 외부 체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, true, false, false, false, 2),// 외부 체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, false, true, true, true, 2),  // 외부 언체크 예외, 내부 트랜잭션, 체크 예외
                Arguments.of(false, false, true, true, false, 0), // 외부 언체크 예외, 내부 트랜잭션, 언체크 예외
                Arguments.of(false, false, true, false, true, 2), // 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, false, true, false, false, 2),// 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, false, false, true, true, 2), // 외부 언체크 예외, 내부 비트랜잭션, 체크 예외
                Arguments.of(false, false, false, true, false, 0),// 외부 언체크 예외, 내부 비트랜잭션, 언체크 예외
                Arguments.of(false, false, false, false, true, 2),// 외부 언체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, false, false, false, false, 2) // 외부 언체크 예외, 내부 비트랜잭션, 정상
        );
    }

    private static Stream<Arguments> outerNonTxTestParameters() {
        return Stream.of(
                // outerThrow, outerChecked, innerTransactional, innerThrow, innerChecked, expectedCount
                Arguments.of(true, true, true, true, true, 2),   // 모든 예외 발생, 체크 예외, 트랜잭션
                Arguments.of(true, true, true, true, false, 2),  // 모든 예외 발생, 체크 예외, 트랜잭션
                Arguments.of(true, true, true, false, true, 2),  // 외부 체크 예외, 내부 정상
                Arguments.of(true, true, true, false, false, 2), // 외부 체크 예외, 내부 정상
                Arguments.of(true, true, false, true, true, 2),  // 외부 체크 예외, 내부 비트랜잭션
                Arguments.of(true, true, false, true, false, 2), // 외부 체크 예외, 내부 비트랜잭션
                Arguments.of(true, true, false, false, true, 2), // 외부 체크 예외, 내부 비트랜잭션, 내부 정상
                Arguments.of(true, true, false, false, false, 2),// 외부 체크 예외, 내부 비트랜잭션, 내부 정상
                Arguments.of(true, false, true, true, true, 2),  // 외부 언체크 예외, 내부 트랜잭션, 체크 예외
                Arguments.of(true, false, true, true, false, 2), // 외부 언체크 예외, 내부 트랜잭션, 언체크 예외
                Arguments.of(true, false, true, false, true, 2), // 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(true, false, true, false, false, 2),// 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(true, false, false, true, true, 2), // 외부 언체크 예외, 내부 비트랜잭션, 체크 예외
                Arguments.of(true, false, false, true, false, 2),// 외부 언체크 예외, 내부 비트랜잭션, 언체크 예외
                Arguments.of(true, false, false, false, true, 2),// 외부 언체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(true, false, false, false, false, 2),// 외부 언체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, true, true, true, true, 2),   // 외부 체크 예외, 내부 트랜잭션, 체크 예외
                Arguments.of(false, true, true, true, false, 2),  // 외부 체크 예외, 내부 트랜잭션, 언체크 예외
                Arguments.of(false, true, true, false, true, 2),  // 외부 체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, true, true, false, false, 2), // 외부 체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, true, false, true, true, 2),  // 외부 체크 예외, 내부 비트랜잭션, 체크 예외
                Arguments.of(false, true, false, true, false, 2), // 외부 체크 예외, 내부 비트랜잭션, 언체크 예외
                Arguments.of(false, true, false, false, true, 2), // 외부 체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, true, false, false, false, 2),// 외부 체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, false, true, true, true, 2),  // 외부 언체크 예외, 내부 트랜잭션, 체크 예외
                Arguments.of(false, false, true, true, false, 2), // 외부 언체크 예외, 내부 트랜잭션, 언체크 예외
                Arguments.of(false, false, true, false, true, 2), // 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, false, true, false, false, 2),// 외부 언체크 예외, 내부 트랜잭션, 정상
                Arguments.of(false, false, false, true, true, 2), // 외부 언체크 예외, 내부 비트랜잭션, 체크 예외
                Arguments.of(false, false, false, true, false, 2),// 외부 언체크 예외, 내부 비트랜잭션, 언체크 예외
                Arguments.of(false, false, false, false, true, 2),// 외부 언체크 예외, 내부 비트랜잭션, 정상
                Arguments.of(false, false, false, false, false, 2) // 외부 언체크 예외, 내부 비트랜잭션, 정상
        );
    }

}