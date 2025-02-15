package ding.co.backendportfolio.chapter4;

import ding.co.backendportfolio.chapter2.entity.Member;
import ding.co.backendportfolio.chapter2.entity.Role;
import ding.co.backendportfolio.chapter2.repository.MemberRepository;
import ding.co.backendportfolio.chapter4.entity.Event;
import ding.co.backendportfolio.chapter4.repository.EventParticipantRepository;
import ding.co.backendportfolio.chapter4.repository.EventRepositoryWithoutLock;
import ding.co.backendportfolio.chapter4.service.EventServiceWithoutLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class EventConcurrencyProblemTest {
    @Autowired
    private EventServiceWithoutLock eventServiceWithoutLock;

    @Autowired
    private EventRepositoryWithoutLock eventRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EventParticipantRepository participantRepository;

    private Event testEvent;
    private List<Long> memberIds;

    @BeforeEach
    void setUp() {
        // 테스트 이벤트 생성 - 최대 100명 참가 가능
        testEvent = Event.builder()
                .name("동시성 문제 테스트 이벤트")
                .description("락이 없는 경우의 문제를 보여주는 테스트")
                .eventDate(LocalDateTime.now().plusDays(7))
                .maxParticipants(100)
                .build();
        testEvent = eventRepository.save(testEvent);

        // 테스트용 회원 200명 생성
        memberIds = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Member member = Member.builder()
                    .email("test" + UUID.randomUUID() + "@test.com")
                    .password("password")
                    .nickname("테스터" + i)
                    .role(Role.USER)
                    .build();
            memberIds.add(memberRepository.save(member).getId());
        }
    }

    @Test
    @DisplayName("동시성 제어가 없을 때 발생하는 문제 - 초과 등록 문제")
    void concurrencyProblemTest() throws InterruptedException {
        // given
        int numberOfThreads = 150;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    eventServiceWithoutLock.joinEventWithoutLock(testEvent.getId(), memberIds.get(index));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        Event updatedEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        long actualParticipantCount = participantRepository.countByEventId(testEvent.getId());

        System.out.println("이벤트 최대 참가 가능 인원: " + updatedEvent.getMaxParticipants());
        System.out.println("DB에 저장된 참가자 수: " + updatedEvent.getCurrentParticipants());
        System.out.println("실제 등록된 참가자 수: " + actualParticipantCount);

        // 참가자 수가 최대 인원을 초과했거나
        // DB의 currentParticipants와 실제 등록된 참가자 수가 다른 경우 테스트 성공
        boolean hasInconsistency = updatedEvent.getCurrentParticipants() > updatedEvent.getMaxParticipants() ||
                updatedEvent.getCurrentParticipants() != actualParticipantCount;

//        assertThat(hasInconsistency).isTrue();
    }
} 