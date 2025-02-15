package ding.co.backendportfolio.chapter4.repository;

import ding.co.backendportfolio.chapter4.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

@Repository
public interface EventRepositoryWithoutLock extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.id = :id")
    Optional<Event> findByIdWithoutLock(@Param("id") Long id);
    
    @Modifying
    @Query("UPDATE Event e SET e.currentParticipants = e.currentParticipants + 1 WHERE e.id = :eventId")
    void increaseParticipantCount(@Param("eventId") Long eventId);
} 