package com.onlyjoon.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService1 {

    private final TestRepository testRepository;

    @Transactional
    public void outerThrowUnchecked() {
        Code outerEntity = new Code();
        outerEntity.setCode("OUTER_TX_THROW_UNCHECK");
        outerEntity.setEtc("OUTER_TX_THROW_UNCHECK");
        testRepository.save(outerEntity);
        innerTxThrowUnchecked();
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
