package ding.co.backendportfolio.chapter5.async_operation.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class AsyncMovieRecommendClient {

    @Async("asyncExecutor")
    public CompletableFuture<List<Long>> getRecommendedMovieIds(Long userNo, long delaySeconds) {
        try {
            Thread.sleep(delaySeconds * 1000L);

            List<Long> recommendedMovieIds = new ArrayList<>(5);
            for (int i = 0; i < 5; i++) {
                long randomLong = ThreadLocalRandom.current().nextLong();
                recommendedMovieIds.add(randomLong);
            }

            return CompletableFuture.completedFuture(recommendedMovieIds);
        } catch (InterruptedException e) {
            log.warn("Movie Recommendation was interrupted", e);
            Thread.currentThread().interrupt(); // interrupt 상태 복원
            return CompletableFuture.completedFuture(Collections.emptyList());
        } catch (Exception e) {
            log.warn("Failed to recommend movies.", e);
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
    }

}
