package ding.co.backendportfolio.chapter5.async_operation.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AsyncMovieInterestEmitter {

    @Async("emitExecutor")
    public void emitUserInterest(Long userNo, Long movieId, long l) {
        try {
            Thread.sleep(l * 1000L);
        } catch (InterruptedException e) {
            log.warn("Movie Interest Emission was interrupted. movieId: {}", movieId, e);
            Thread.currentThread().interrupt(); // interrupt 상태 복원
        } catch (Exception e) {
            log.warn("Failed to emit moview Interest. movieId: {}", movieId, e);
        }
    }
}
