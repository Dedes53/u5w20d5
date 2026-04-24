package federicolepore.u5w20d5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "booked_at", nullable = false)
    private LocalDate bookedAt;

    public Reservation(User user, Event event) {
        this.user = user;
        this.event = event;
        this.bookedAt = LocalDate.now();
    }


}
