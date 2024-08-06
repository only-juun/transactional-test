package com.onlyjoon.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService4 {

    private final TestRepository testRepository;

    @Transactional
    public void outerCatchChecked() {
        try {
            Code outerEntity = new Code();
            outerEntity.setCode("OUTER_NON_TX_CATCH_CHECK");
            outerEntity.setEtc("OUTER_NON_TX_CATCH_CHECK");
            testRepository.save(outerEntity);
            innerTxThrowChecked();
        } catch (IOException e) {
            // 예외 캐치
        }
    }

    @Transactional
    public void innerTxThrowChecked() throws IOException {
        Code innerEntity = new Code();
        innerEntity.setCode("INNER_TX_THROW_CHECK");
        innerEntity.setEtc("INNER_TX_THROW_CHECK");
        testRepository.save(innerEntity);
        throw new IOException("Checked Exception in Inner Transactional Method");
    }
}