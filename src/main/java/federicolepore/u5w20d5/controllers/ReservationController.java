package federicolepore.u5w20d5.controllers;

import federicolepore.u5w20d5.entities.Reservation;
import federicolepore.u5w20d5.entities.User;
import federicolepore.u5w20d5.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // prenota un posto ad un evento
    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation reserve(@PathVariable UUID id,
                               @AuthenticationPrincipal User user) {
        return reservationService.reserve(id, user);
    }

    // visualizza prenotazioni per utente
    @GetMapping("/me")
    public List<Reservation> getMyReservations(@AuthenticationPrincipal User user) {
        return reservationService.getUserReservation(user);
    }

    //    cancella prenotazioen
    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable UUID resId,
                                  @AuthenticationPrincipal User user) {
        reservationService.deleteReservation(resId, user);
    }

}
