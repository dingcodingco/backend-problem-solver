package ding.co.backendportfolio.chapter5.async_operation.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class AsyncMovieBookingClient {
    @Async("asyncExecutor")
    public CompletableFuture<Boolean> isAvailable(Long movieId, long delaySeconds) {
        try {
            Thread.sleep(delaySeconds * 1000L);
            boolean result = ThreadLocalRandom.current().nextBoolean();
            return CompletableFuture.completedFuture(result);
        } catch (InterruptedException e) {
            log.warn("Movie availability check was interrupted. movieId: {}", movieId, e);
            Thread.currentThread().interrupt(); // interrupt 상태 복원
            return CompletableFuture.completedFuture(false);
        } catch (Exception e) {
            log.warn("Failed to get movie ranking. movieId: {}", movieId, e);
            return CompletableFuture.completedFuture(false);
        }
    }
}
