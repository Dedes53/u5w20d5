package federicolepore.u5w20d5.controllers;

import federicolepore.u5w20d5.entities.Event;
import federicolepore.u5w20d5.entities.User;
import federicolepore.u5w20d5.payloads.EventDTO;
import federicolepore.u5w20d5.services.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //  RICERCA DEGLI EVENTI (accessibile a tutti)
    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "date") String sortBy) {
        return eventService.getAllEvents(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable UUID id) {
        return eventService.getById(id);
    }


    //    GESTIONE DEGLI EVENTI (solo per l'organizzatore dell'evento)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event createEvent(@RequestBody @Valid EventDTO body,
                             @AuthenticationPrincipal User organizer) {
        return eventService.save(body, organizer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event updateEvent(@PathVariable UUID id,
                             @RequestBody @Valid EventDTO body,
                             @AuthenticationPrincipal User organizer) {
        return eventService.updateEvent(id, body, organizer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public void deleteEvent(@PathVariable UUID id,
                            @AuthenticationPrincipal User organizer) {
        eventService.delete(id, organizer);
    }

}
