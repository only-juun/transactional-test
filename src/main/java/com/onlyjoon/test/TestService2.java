package com.onlyjoon.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TestService2 {

    private final TestRepository testRepository;

@Transactional
public void outerThrowChecked() throws IOException {
    Code outerEntity = new Code();
    outerEntity.setCode("OUTER_TX_THROW_CHECK");
    outerEntity.setEtc("OUTER_TX_THROW_CHECK");
    testRepository.save(outerEntity);
    innerTxThrowChecked();
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
