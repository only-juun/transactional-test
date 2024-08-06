package com.onlyjoon.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService3 {

    private final TestRepository testRepository;

    @Transactional
    public void outerCatchUnchecked() {
        try {
            Code outerEntity = new Code();
            outerEntity.setCode("OUTER_NON_TX_CATCH_UNCHECK");
            outerEntity.setEtc("OUTER_NON_TX_CATCH_UNCHECK");
            testRepository.save(outerEntity);
            innerTxThrowUnchecked();
        } catch (RuntimeException e) {
            // 예외 캐치
        }
    }

    @Transactional
    public void innerTxThrowUnchecked() {
        Code innerEntity = new Code();
        innerEntity.setCode("INNER_TX_THROW_UNCHECK");
        innerEntity.setEtc("INNER_TX_THROW_UNCHECK");
        testRepository.save(innerEntity);
        throw new RuntimeException("Unchecked Exception in Inner Transactional Method");
    }
}
