package federicolepore.u5w20d5.services;

import federicolepore.u5w20d5.entities.Event;
import federicolepore.u5w20d5.entities.Role;
import federicolepore.u5w20d5.entities.User;
import federicolepore.u5w20d5.exceptions.BadRequestException;
import federicolepore.u5w20d5.exceptions.NotFoundException;
import federicolepore.u5w20d5.exceptions.UnauthorizedException;
import federicolepore.u5w20d5.payloads.EventDTO;
import federicolepore.u5w20d5.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public Page<Event> getAllEvents(int page, int size, String sortBy) {
        if (size > 100) size = 10;
        if (size < 0) size = 1;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventRepository.findAll(pageable);
    }

    public Event getById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Non è stato trovato nessun evento associato all'id:  " + id));
    }

    public Event save(EventDTO body, User organizer) {

        if (eventRepository.existsByTitleAndDateAndLocation(body.title(), body.date(), body.location()))
            throw new BadRequestException("Esiste già un evento con titolo '" + body.title() +
                    "' il " + body.date() + " a " + body.location());
        if (!organizer.getRole().equals(Role.ORGANIZER))
            throw new UnauthorizedException("Solo gli organizzatori possono creare eventi");

        Event e = new Event(body.title(), body.description(), body.date(),
                body.location(), body.seatsAvailable(), organizer);
        return eventRepository.save(e);
    }

    public Event updateEvent(UUID id, EventDTO body, User currentUser) {
        Event event = getById(id);
        if (!event.getUser().getId().equals(currentUser.getId()))
            throw new UnauthorizedException("Solo il creatore dell'evento è autorizzato a modificare il proprio evento");
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(body.date());
        event.setLocation(body.location());
        event.setSeatsAvailable(body.seatsAvailable());
        return eventRepository.save(event);
    }

    public void delete(UUID id, User currentUser) {
        Event event = getById(id);
        if (!event.getUser().getId().equals(currentUser.getId()))
            throw new UnauthorizedException("Solo il creatore dell'evento è autorizzato a cancellare il proprio evento");
        eventRepository.delete(event);
    }


}
