package ding.co.backendportfolio.chapter4.service;

import ding.co.backendportfolio.chapter4.entity.Event;
import ding.co.backendportfolio.chapter4.entity.EventParticipant;
import ding.co.backendportfolio.chapter4.repository.EventRepository;
import ding.co.backendportfolio.chapter4.repository.EventParticipantRepository;
import ding.co.backendportfolio.chapter2.entity.Member;
import ding.co.backendportfolio.chapter2.repository.MemberRepository;
import ding.co.backendportfolio.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.OptimisticLockingFailureException;
import ding.co.backendportfolio.chapter4.exception.ConcurrencyException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final EventParticipantRepository participantRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void joinEventOptimistic(Long eventId, Long memberId) {
        Event event = eventRepository.findByIdWithOptimisticLock(eventId)
                .orElseThrow(() -> new EntityNotFoundException("이벤트를 찾을 수 없습니다."));
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        try {
            event.increaseParticipants();
            EventParticipant participant = EventParticipant.builder()
                    .event(event)
                    .member(member)
                    .build();
            
            participantRepository.save(participant);
            
        } catch (OptimisticLockingFailureException e) {
            log.error("낙관적 락 충돌 발생: {}", e.getMessage());
            throw new ConcurrencyException("다른 사용자가 먼저 등록했습니다. 다시 시도해주세요.");
        }
    }

    @Transactional
    public void joinEventPessimistic(Long eventId, Long memberId) {
        Event event = eventRepository.findByIdWithPessimisticLock(eventId)
                .orElseThrow(() -> new EntityNotFoundException("이벤트를 찾을 수 없습니다."));
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
        
        event.increaseParticipants();
        EventParticipant participant = EventParticipant.builder()
                .event(event)
                .member(member)
                .build();
        
        participantRepository.save(participant);
    }
} 