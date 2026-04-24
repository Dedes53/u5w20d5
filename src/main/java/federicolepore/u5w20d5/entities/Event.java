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
@Table(name = "event")
public class Event {


    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID Id;


    private String title;
    private String description;
    private LocalDate date;
    private String location;
    @Column(name = "seats_available")
    private int seatsAvailable;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User user;

    public Event(String title, String description, LocalDate date, String location, int seatsAvailable, User user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.seatsAvailable = seatsAvailable;
        this.user = user;
    }
    
}
