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
    public void outerTransactionalMethod(boolean outerThrow, boolean outerChecked, boolean innerTransactional, boolean innerThrow, boolean innerChecked) throws IOException {
        if (outerThrow) {
            if (outerChecked) {
                // outer 에서 체크예외 발생 후 밖으로 던지는 경우
                Test outerEntity = new Test();
                outerEntity.setCode("OUTER_TX_THROW_CHECK");
                outerEntity.setEtc("OUTER_TX_THROW_CHECK");
                testRepository.save(outerEntity);

                if (innerTransactional) {
                    innerTransactionalMethod(innerThrow, innerChecked);
                } else {
                    innerNonTransactionalMethod(innerThrow, innerChecked);
                }

                throw new IOException("Outer Transactional failed with IOException");
            } else {
                // outer 에서 언체크예외 발생 후 밖으로 던지는 경우
                Test outerEntity = new Test();
                outerEntity.setCode("OUTER_TX_THROW_UNCHECK");
                outerEntity.setEtc("OUTER_TX_THROW_UNCHECK");
                testRepository.save(outerEntity);

                if (innerTransactional) {
                    innerTransactionalMethod(innerThrow, innerChecked);
                } else {
                    innerNonTransactionalMethod(innerThrow, innerChecked);
                }

                throw new RuntimeException("Outer Transactional failed with RuntimeException");
            }
        } else {
            if (outerChecked) {
                try {
                    // outer 에서 체크예외 발생 후 잡는 경우
                    Test outerEntity = new Test();
                    outerEntity.setCode("OUTER_NON_TX_CATCH_CHECK");
                    outerEntity.setEtc("OUTER_NON_TX_CATCH_CHECK");
                    testRepository.save(outerEntity);

                    if (innerTransactional) {
                        innerTransactionalMethod(innerThrow, innerChecked);
                    } else {
                        innerNonTransactionalMethod(innerThrow, innerChecked);
                    }
                    throw new IOException("Outer Transactional failed with IOException");
                } catch (IOException | RuntimeException e) {
                    handleException(e, innerThrow);
                }
            } else {
                try {
                    // outer 에서 언체크예외 발생 후 잡는 경우
                    Test outerEntity = new Test();
                    outerEntity.setCode("OUTER_NON_TX_CATCH_UNCHECK");
                    outerEntity.setEtc("OUTER_NON_TX_CATCH_UNCHECK");
                    testRepository.save(outerEntity);

                    if (innerTransactional) {
                        innerTransactionalMethod(innerThrow, innerChecked);
                    } else {
                        innerNonTransactionalMethod(innerThrow, innerChecked);
                    }
                    throw new RuntimeException("Outer Transactional failed with RuntimeException");
                } catch (IOException | RuntimeException e) {
                    handleException(e, innerThrow);
                }
            }
        }
    }

    public void outerNonTransactionalMethod(boolean outerThrow, boolean outerChecked, boolean innerTransactional, boolean innerThrow, boolean innerChecked) throws IOException {
        if (outerThrow) {
            if (outerChecked) {
                // outer 에서 체크예외 발생 후 밖으로 던지는 경우
                Test outerEntity = new Test();
                outerEntity.setCode("OUTER_NON_TX_THROW_CHECK");
                outerEntity.setEtc("OUTER_NON_TX_THROW_CHECK");
                testRepository.save(outerEntity);

                if (innerTransactional) {
                    innerTransactionalMethod(innerThrow, innerChecked);
                } else {
                    innerNonTransactionalMethod(innerThrow, innerChecked);
                }

                throw new IOException("Outer Transactional failed with IOException");
            } else {
                // outer 에서 언체크예외 발생 후 밖으로 던지는 경우
                Test outerEntity = new Test();
                outerEntity.setCode("OUTER_NON_TX_THROW_UNCHECK");
                outerEntity.setEtc("OUTER_NON_TX_THROW_UNCHECK");
                testRepository.save(outerEntity);

                if (innerTransactional) {
                    innerTransactionalMethod(innerThrow, innerChecked);
                } else {
                    innerNonTransactionalMethod(innerThrow, innerChecked);
                }

                throw new RuntimeException("Outer Transactional failed with RuntimeException");
            }
        } else {
            if (outerChecked) {
                try {
                    // outer 에서 체크예외 발생 후 잡는 경우
                    Test outerEntity = new Test();
                    outerEntity.setCode("OUTER_NON_TX_CATCH_CHECK");
                    outerEntity.setEtc("OUTER_NON_TX_CATCH_CHECK");
                    testRepository.save(outerEntity);

                    if (innerTransactional) {
                        innerTransactionalMethod(innerThrow, innerChecked);
                    } else {
                        innerNonTransactionalMethod(innerThrow, innerChecked);
                    }
                    throw new IOException("Outer Transactional failed with IOException");
                } catch (IOException | RuntimeException e) {
                    handleException(e, innerThrow);
                }
            } else {
                try {
                    // outer 에서 언체크예외 발생 후 잡는 경우
                    Test outerEntity = new Test();
                    outerEntity.setCode("OUTER_NON_TX_CATCH_UNCHECK");
                    outerEntity.setEtc("OUTER_NON_TX_CATCH_UNCHECK");
                    testRepository.save(outerEntity);

                    if (innerTransactional) {
                        innerTransactionalMethod(innerThrow, innerChecked);
                    } else {
                        innerNonTransactionalMethod(innerThrow, innerChecked);
                    }
                    throw new RuntimeException("Outer Transactional failed with RuntimeException");
                } catch (IOException | RuntimeException e) {
                    handleException(e, innerThrow);
                }
            }
        }
    }

    @Transactional
    public void innerTransactionalMethod(boolean innerThrow, boolean innerChecked) throws IOException {
        if (innerThrow) {
            if (innerChecked) {
                // inner 에서 체크예외 발생 후 밖으로 던지는 경우
                Test innerEntity = new Test();
                innerEntity.setCode("INNER_TX_THROW_CHECK");
                innerEntity.setEtc("INNER_TX_THROW_CHECK");
                testRepository.save(innerEntity);
                throw new IOException("Checked Exception in Inner Transactional Method");
            } else {
                // inner 에서 언체크예외 발생 후 밖으로 던지는 경우
                Test innerEntity = new Test();
                innerEntity.setCode("INNER_TX_THROW_UNCHECK");
                innerEntity.setEtc("INNER_TX_THROW_UNCHECK");
                testRepository.save(innerEntity);
                throw new RuntimeException("Unchecked Exception in Inner Transactional Method");
            }
        } else {
            if (innerChecked) {
                try {
                    // inner 에서 체크예외 발생 후 잡는 경우
                    Test innerEntity = new Test();
                    innerEntity.setCode("INNER_TX_CATCH_CHECK");
                    innerEntity.setEtc("INNER_TX_CATCH_CHECK");
                    testRepository.save(innerEntity);
                    throw new IOException("Checked Exception in Inner Transactional Method");
                } catch (IOException e) {
                    handleException(e, innerThrow);
                }
            } else {
                try {
                    // inner 에서 언체크예외 발생 후 잡는 경우
                    Test innerEntity = new Test();
                    innerEntity.setCode("INNER_TX_CATCH_UNCHECK");
                    innerEntity.setEtc("INNER_TX_CATCH_UNCHECK");
                    testRepository.save(innerEntity);
                    throw new RuntimeException("Unchecked Exception in Inner Transactional Method");
                } catch (RuntimeException e) {
                    handleException(e, innerThrow);
                }
            }
        }
    }

    public void innerNonTransactionalMethod(boolean innerThrow, boolean innerChecked) throws IOException {
        if (innerThrow) {
            if (innerChecked) {
                // inner 에서 체크예외 발생 후 밖으로 던지는 경우
                Test innerEntity = new Test();
                innerEntity.setCode("INNER_NON_TX_THROW_CHECK");
                innerEntity.setEtc("INNER_NON_TX_THROW_CHECK");
                testRepository.save(innerEntity);
                throw new IOException("Checked Exception in Inner Transactional Method");
            } else {
                // inner 에서 언체크예외 발생 후 밖으로 던지는 경우
                Test innerEntity = new Test();
                innerEntity.setCode("INNER_NON_TX_THROW_UNCHECK");
                innerEntity.setEtc("INNER_NON_TX_THROW_UNCHECK");
                testRepository.save(innerEntity);
                throw new RuntimeException("Unchecked Exception in Inner Transactional Method");
            }
        } else {
            if (innerChecked) {
                try {
                    // inner 에서 체크예외 발생 후 잡는 경우
                    Test innerEntity = new Test();
                    innerEntity.setCode("INNER_NON_TX_CATCH_CHECK");
                    innerEntity.setEtc("INNER_NON_TX_CATCH_CHECK");
                    testRepository.save(innerEntity);
                    throw new IOException("Checked Exception in Inner Transactional Method");
                } catch (IOException e) {
                    handleException(e, innerThrow);
                }
            } else {
                try {
                    // inner 에서 언체크예외 발생 후 잡는 경우
                    Test innerEntity = new Test();
                    innerEntity.setCode("INNER_NON_TX_CATCH_UNCHECK");
                    innerEntity.setEtc("INNER_NON_TX_CATCH_UNCHECK");
                    testRepository.save(innerEntity);
                    throw new RuntimeException("Unchecked Exception in Inner Transactional Method");
                } catch (RuntimeException e) {
                    handleException(e, innerThrow);
                }
            }
        }
    }

    private void handleException(Exception e, boolean throwError) throws IOException, RuntimeException {
        if (throwError) {
            if (e instanceof IOException) {
                throw (IOException) e;
            } else if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
        }
    }

}