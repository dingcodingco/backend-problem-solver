package ding.co.backendportfolio.chapter4.controller;

import ding.co.backendportfolio.chapter4.service.EventService;
import ding.co.backendportfolio.chapter2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chapter4/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final MemberService memberService;
    
    @PostMapping("/{eventId}/join/optimistic")
    public ResponseEntity<Void> joinEventOptimistic(
            @PathVariable Long eventId,
            @AuthenticationPrincipal User user) {
        Long memberId = memberService.getCurrentMember(user.getUsername()).getId();
        eventService.joinEventOptimistic(eventId, memberId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{eventId}/join/pessimistic")
    public ResponseEntity<Void> joinEventPessimistic(
            @PathVariable Long eventId,
            @AuthenticationPrincipal User user) {
        Long memberId = memberService.getCurrentMember(user.getUsername()).getId();
        eventService.joinEventPessimistic(eventId, memberId);
        return ResponseEntity.ok().build();
    }
} 