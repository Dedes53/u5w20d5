package federicolepore.u5w20d5.repositories;

import federicolepore.u5w20d5.entities.Event;
import federicolepore.u5w20d5.entities.Reservation;
import federicolepore.u5w20d5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByUserAndEvent(User user, Event event);

    List<Reservation> findByUser(User user);

    Optional<Reservation> findByUserAndEvent(User user, Event event);
}
