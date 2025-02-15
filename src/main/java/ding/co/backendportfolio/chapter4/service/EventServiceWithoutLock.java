package ding.co.backendportfolio.chapter4.service;

import ding.co.backendportfolio.chapter4.entity.Event;
import ding.co.backendportfolio.chapter4.entity.EventParticipant;
import ding.co.backendportfolio.chapter4.repository.EventParticipantRepository;
import ding.co.backendportfolio.chapter4.repository.EventRepositoryWithoutLock;
import ding.co.backendportfolio.chapter2.entity.Member;
import ding.co.backendportfolio.chapter2.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceWithoutLock {
    private final EventRepositoryWithoutLock eventRepository;
    private final EventParticipantRepository participantRepository;
    private final MemberRepository memberRepository;

    public void joinEventWithoutLock(Long eventId, Long memberId) {
        Event event = eventRepository.findByIdWithoutLock(eventId)
                .orElseThrow(() -> new EntityNotFoundException("이벤트를 찾을 수 없습니다."));
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        event.increaseParticipants();
        eventRepository.save(event);
        
        EventParticipant participant = EventParticipant.builder()
                .event(event)
                .member(member)
                .build();
        
        participantRepository.save(participant);
    }
} 