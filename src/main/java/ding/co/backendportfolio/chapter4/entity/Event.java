package ding.co.backendportfolio.chapter4.entity;

import ding.co.backendportfolio.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ch4_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime eventDate;
    private int maxParticipants;
    private int currentParticipants;
    
    @Version
    private Long version;
    
    @Builder
    public Event(String name, String description, LocalDateTime eventDate, int maxParticipants) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = 0;
    }
    
    public void increaseParticipants() {
        if (this.currentParticipants >= this.maxParticipants) {
            throw new IllegalStateException("참가 인원이 초과되었습니다.");
        }
        this.currentParticipants++;
    }
} 