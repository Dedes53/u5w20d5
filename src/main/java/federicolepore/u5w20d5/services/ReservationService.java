package federicolepore.u5w20d5.services;

import federicolepore.u5w20d5.entities.Event;
import federicolepore.u5w20d5.entities.Reservation;
import federicolepore.u5w20d5.entities.User;
import federicolepore.u5w20d5.exceptions.BadRequestException;
import federicolepore.u5w20d5.exceptions.NotFoundException;
import federicolepore.u5w20d5.exceptions.UnauthorizedException;
import federicolepore.u5w20d5.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventService eventService;

    public ReservationService(ReservationRepository reservationRepository, EventService eventService) {
        this.reservationRepository = reservationRepository;
        this.eventService = eventService;
    }


    public Reservation reserve(UUID eventId, User user) {
        Event event = eventService.getById(eventId);
        if (event.getSeatsAvailable() <= 0)
            throw new BadRequestException("Non ci sono posti disponibili per questo evento");
        if (reservationRepository.existsByUserAndEvent(user, event))
            throw new BadRequestException("Hai già prenotato un posto per questo evento");
        if (event.getUser().getId().equals(user.getId()))
            throw new BadRequestException("Sei l'organizzatore, non puoi prenotare al tuo stesso evento");

        event.setSeatsAvailable(event.getSeatsAvailable() - 1);
        Reservation r = new Reservation(user, event);
        return reservationRepository.save(r);
    }

    public List<Reservation> getUserReservation(User user) {
        return reservationRepository.findByUser(user);
    }

    public void deleteReservation(UUID reservationId, User currentUser) {
        Reservation r = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("La prenotazione con id " + reservationId + " non è stata trovata"));
        if (!r.getUser().getId().equals(currentUser.getId()))
            throw new UnauthorizedException("Non sei autorizzato a cancellare questa prenotazione");

        Event event = r.getEvent();
        event.setSeatsAvailable(event.getSeatsAvailable() + 1);
        reservationRepository.delete(r);
    }


}
