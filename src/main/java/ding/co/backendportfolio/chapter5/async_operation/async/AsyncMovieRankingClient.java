package ding.co.backendportfolio.chapter5.async_operation.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class AsyncMovieRankingClient {
    @Async("asyncExecutor")
    public CompletableFuture<Integer> getRanking(Long movieId, long delaySeconds) {
        try {
            Thread.sleep(delaySeconds * 1000L);
            return CompletableFuture.completedFuture(ThreadLocalRandom.current().nextInt(1, 10));
        } catch (InterruptedException e) {
            log.warn("Movie ranking check was interrupted. movieId: {}", movieId, e);
            Thread.currentThread().interrupt(); // interrupt 상태 복원
            return CompletableFuture.completedFuture(0);
        } catch (Exception e) {
            log.warn("Failed to get movie ranking. movieId: {}", movieId, e);
            return CompletableFuture.completedFuture(0);
        }
    }
}
