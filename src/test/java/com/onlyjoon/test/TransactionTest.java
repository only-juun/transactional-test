package com.onlyjoon.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
    @MethodSource("provideTestParameters")
    void testTransactionScenarios(String methodName, boolean innerTransactional, int expectedCount) {
        try {
            Method method = TestService.class.getMethod(methodName, boolean.class);
            method.invoke(testService, innerTransactional);
        } catch (InvocationTargetException e) {
            // 실제 예외 처리
            Throwable cause = e.getCause();
            if (cause instanceof IOException || cause instanceof RuntimeException) {
                // 예외 무시 또는 로깅
            } else {
                throw new RuntimeException(cause);
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedCount, testRepository.findAll().size());
    }

    private static Stream<Arguments> provideTestParameters() {
        List<Arguments> arguments = new ArrayList<>();
        String[] methods = {
                "outerThrowChecked",
                "outerThrowUnchecked",
                "outerCatchChecked",
                "outerCatchUnchecked",
                "outerNonTxThrowChecked",
                "outerNonTxThrowUnchecked",
                "outerNonTxCatchChecked",
                "outerNonTxCatchUnchecked"
        };

        boolean[] innerTransactionalOptions = {true, false};
        // 설정된 조건에 따라 expectedCount 를 수정하여 실제 기대되는 값으로 설정합니다.
        for (String method : methods) {
            for (boolean innerTransactional : innerTransactionalOptions) {
                // 예외가 발생하는지 여부와 트랜잭션의 존재에 따라 예상 결과를 설정
                int expectedCount = calculateExpectedCount(method);
                arguments.add(Arguments.of(method, innerTransactional, expectedCount));
            }
        }
        return arguments.stream();
    }

    private static int calculateExpectedCount(String methodName) {
        if (methodName.contains("outerThrowChecked") || methodName.contains("outerNonTxThrowChecked")) {
            // [1], [2], [9], [10]
            return 2; // 항상 저장
        }
        if (methodName.contains("outerThrowUnchecked")) {
            // [3], [4]
            return 0; // 롤백
        }
        if (methodName.contains("outerCatchChecked") || methodName.contains("outerCatchUnchecked")) {
            // [5], [6], [7], [8]
            return 2; // 항상 저장
        }
        if (methodName.contains("outerNonTxThrowUnchecked")) {
            // [11], [12]
            return 2; // 항상 저장
        }
        if (methodName.contains("outerNonTxCatchChecked") || methodName.contains("outerNonTxCatchUnchecked")) {
            // [13], [14], [15], [16]
            return 2; // 항상 저장
        }
        return 0;
    }
}