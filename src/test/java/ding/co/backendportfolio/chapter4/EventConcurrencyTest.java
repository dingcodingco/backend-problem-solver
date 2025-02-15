package ding.co.backendportfolio.chapter4;

import ding.co.backendportfolio.chapter4.entity.Event;
import ding.co.backendportfolio.chapter4.repository.EventRepository;
import ding.co.backendportfolio.chapter2.entity.Member;
import ding.co.backendportfolio.chapter2.entity.Role;
import ding.co.backendportfolio.chapter2.repository.MemberRepository;
import ding.co.backendportfolio.chapter4.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.UUID;

@SpringBootTest
class EventConcurrencyTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MemberRepository memberRepository;
    
    @BeforeEach
    void setUp() {
        // 테스트용 이벤트 생성
        Event event = Event.builder()
                .name("테스트 이벤트")
                .description("동시성 테스트")
                .eventDate(LocalDateTime.now().plusDays(7))
                .maxParticipants(100)
                .build();
        eventRepository.save(event);
        
        // 테스트용 회원들 생성 - UUID를 사용하여 고유한 이메일 생성
        for (int i = 0; i < 200; i++) {
            Member member = Member.builder()
                    .email("test" + UUID.randomUUID().toString() + "@test.com")
                    .password("password")
                    .nickname("테스터" + i)
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
        }
    }
    
    @Test
    void compareOptimisticAndPessimisticLock() throws InterruptedException {
        int numberOfThreads = 100;
        int numberOfUsers = 100;
        
        // 낙관적 락 테스트
        long startTimeOptimistic = System.currentTimeMillis();
        executeConcurrentJoins(numberOfThreads, numberOfUsers, true);
        long optimisticTime = System.currentTimeMillis() - startTimeOptimistic;
        
        // 데이터 초기화
        setUp();
        
        // 비관적 락 테스트
        long startTimePessimistic = System.currentTimeMillis();
        executeConcurrentJoins(numberOfThreads, numberOfUsers, false);
        long pessimisticTime = System.currentTimeMillis() - startTimePessimistic;
        
        // 결과 출력
        System.out.println("낙관적 락 실행 시간: " + optimisticTime + "ms");
        System.out.println("비관적 락 실행 시간: " + pessimisticTime + "ms");
    }
    
    private void executeConcurrentJoins(int numberOfThreads, int numberOfUsers, boolean useOptimistic) 
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfUsers);
        
        for (int i = 0; i < numberOfUsers; i++) {
            final long memberId = (long) (i + 1);
            executorService.submit(() -> {
                try {
                    if (useOptimistic) {
                        eventService.joinEventOptimistic(1L, memberId);
                    } else {
                        eventService.joinEventPessimistic(1L, memberId);
                    }
                } catch (Exception e) {
                    // 예외 로깅
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executorService.shutdown();
    }
} 