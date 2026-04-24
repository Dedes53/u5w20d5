package federicolepore.u5w20d5.repositories;

import federicolepore.u5w20d5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    boolean existsByTitleAndDateAndLocation(String title, LocalDate date, String location);
}
