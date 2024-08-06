package com.onlyjoon.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public void outerThrowChecked(boolean innerTransactional) throws IOException {
        Code outerEntity = new Code();
        outerEntity.setCode("OUTER_TX_THROW_CHECK");
        outerEntity.setEtc("OUTER_TX_THROW_CHECK");
        testRepository.save(outerEntity);

        if (innerTransactional) {
            innerTxThrowChecked();
        } else {
            innerNonTxThrowChecked();
        }

        throw new IOException("Outer Transactional failed with IOException");
    }

    @Transactional
    public void outerThrowUnchecked(boolean innerTransactional) {
        Code outerEntity = new Code();
        outerEntity.setCode("OUTER_TX_THROW_UNCHECK");
        outerEntity.setEtc("OUTER_TX_THROW_UNCHECK");
        testRepository.save(outerEntity);

        if (innerTransactional) {
            innerTxThrowUnchecked();
        } else {
            innerNonTxThrowUnchecked();
        }

        throw new RuntimeException("Outer Transactional failed with RuntimeException");
    }

    @Transactional
    public void outerCatchChecked(boolean innerTransactional) {
        try {
            Code outerEntity = new Code();
            outerEntity.setCode("OUTER_NON_TX_CATCH_CHECK");
            outerEntity.setEtc("OUTER_NON_TX_CATCH_CHECK");
            testRepository.save(outerEntity);

            if (innerTransactional) {
                innerTxCatchChecked();
            } else {
                innerNonTxCatchChecked();
            }
            throw new IOException("Outer Transactional failed with IOException");
        } catch (IOException | RuntimeException e) {
            //
        }
    }

    @Transactional
    public void outerCatchUnchecked(boolean innerTransactional) {
        try {
            Code outerEntity = new Code();
            outerEntity.setCode("OUTER_NON_TX_CATCH_UNCHECK");
            outerEntity.setEtc("OUTER_NON_TX_CATCH_UNCHECK");
            testRepository.save(outerEntity);

            if (innerTransactional) {
                innerTxCatchUnchecked();
            } else {
                innerNonTxCatchUnchecked();
            }
            throw new RuntimeException("Outer Transactional failed with RuntimeException");
        } catch (RuntimeException e) {
            //
        }
    }

    public void outerNonTxThrowChecked(boolean innerTransactional) throws IOException {
        Code outerEntity = new Code();
        outerEntity.setCode("OUTER_NON_TX_THROW_CHECK");
        outerEntity.setEtc("OUTER_NON_TX_THROW_CHECK");
        testRepository.save(outerEntity);

        if (innerTransactional) {
            innerTxThrowChecked();
        } else {
            innerNonTxThrowChecked();
        }

        throw new IOException("Outer Non-Transactional failed with IOException");
    }

    public void outerNonTxThrowUnchecked(boolean innerTransactional) {
        Code outerEntity = new Code();
        outerEntity.setCode("OUTER_NON_TX_THROW_UNCHECK");
        outerEntity.setEtc("OUTER_NON_TX_THROW_UNCHECK");
        testRepository.save(outerEntity);

        if (innerTransactional) {
            innerTxThrowUnchecked();
        } else {
            innerNonTxThrowUnchecked();
        }

        throw new RuntimeException("Outer Non-Transactional failed with RuntimeException");
    }

    public void outerNonTxCatchChecked(boolean innerTransactional) {
        try {
            Code outerEntity = new Code();
            outerEntity.setCode("OUTER_NON_TX_CATCH_CHECK");
            outerEntity.setEtc("OUTER_NON_TX_CATCH_CHECK");
            testRepository.save(outerEntity);

            if (innerTransactional) {
                innerTxCatchChecked();
            } else {
                innerNonTxCatchChecked();
            }
            throw new IOException("Outer Non-Transactional failed with IOException");
        } catch (IOException | RuntimeException e) {
            //
        }
    }

    public void outerNonTxCatchUnchecked(boolean innerTransactional) {
        try {
            Code outerEntity = new Code();
            outerEntity.setCode("OUTER_NON_TX_CATCH_UNCHECK");
            outerEntity.setEtc("OUTER_NON_TX_CATCH_UNCHECK");
            testRepository.save(outerEntity);

            if (innerTransactional) {
                innerTxCatchUnchecked();
            } else {
                innerNonTxCatchUnchecked();
            }
            throw new RuntimeException("Outer Non-Transactional failed with RuntimeException");
        } catch (RuntimeException e) {
            //
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

    @Transactional
    public void innerTxThrowUnchecked() {
        Code innerEntity = new Code();
        innerEntity.setCode("INNER_TX_THROW_UNCHECK");
        innerEntity.setEtc("INNER_TX_THROW_UNCHECK");
        testRepository.save(innerEntity);
        throw new RuntimeException("Unchecked Exception in Inner Transactional Method");
    }

    @Transactional
    public void innerTxCatchChecked() {
        try {
            Code innerEntity = new Code();
            innerEntity.setCode("INNER_TX_CATCH_CHECK");
            innerEntity.setEtc("INNER_TX_CATCH_CHECK");
            testRepository.save(innerEntity);
            throw new IOException("Checked Exception in Inner Transactional Method");
        } catch (IOException e) {
            //
        }
    }

    @Transactional
    public void innerTxCatchUnchecked() {
        try {
            Code innerEntity = new Code();
            innerEntity.setCode("INNER_TX_CATCH_UNCHECK");
            innerEntity.setEtc("INNER_TX_CATCH_UNCHECK");
            testRepository.save(innerEntity);
            throw new RuntimeException("Unchecked Exception in Inner Transactional Method");
        } catch (RuntimeException e) {
            //
        }
    }

    public void innerNonTxThrowChecked() throws IOException {
        Code innerEntity = new Code();
        innerEntity.setCode("INNER_NON_TX_THROW_CHECK");
        innerEntity.setEtc("INNER_NON_TX_THROW_CHECK");
        testRepository.save(innerEntity);
        throw new IOException("Checked Exception in Inner Non-Transactional Method");
    }

    public void innerNonTxThrowUnchecked() {
        Code innerEntity = new Code();
        innerEntity.setCode("INNER_NON_TX_THROW_UNCHECK");
        innerEntity.setEtc("INNER_NON_TX_THROW_UNCHECK");
        testRepository.save(innerEntity);
        throw new RuntimeException("Unchecked Exception in Inner Non-Transactional Method");
    }

    public void innerNonTxCatchChecked() {
        try {
            Code innerEntity = new Code();
            innerEntity.setCode("INNER_NON_TX_CATCH_CHECK");
            innerEntity.setEtc("INNER_NON_TX_CATCH_CHECK");
            testRepository.save(innerEntity);
            throw new IOException("Checked Exception in Inner Non-Transactional Method");
        } catch (IOException e) {
            //
        }
    }

    public void innerNonTxCatchUnchecked() {
        try {
            Code innerEntity = new Code();
            innerEntity.setCode("INNER_NON_TX_CATCH_UNCHECK");
            innerEntity.setEtc("INNER_NON_TX_CATCH_UNCHECK");
            testRepository.save(innerEntity);
            throw new RuntimeException("Unchecked Exception in Inner Non-Transactional Method");
        } catch (RuntimeException e) {
            //
        }
    }

}