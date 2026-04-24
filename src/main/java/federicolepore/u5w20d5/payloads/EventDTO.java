package federicolepore.u5w20d5.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventDTO(
        @NotBlank(message = "Il titolo è obbligatorio")
        String title,

        @NotBlank(message = "La descrizione è obbligatoria")
        String description,

        @NotNull(message = "La data è obbligatoria")
        @FutureOrPresent(message = "La data non può essere passata")
        LocalDate date,

        @NotBlank(message = "Il luogo è obbligatorio")
        String location,

        @Size(min = 1, message = "I posti disponibili devono essere almeno 1")
        int seatsAvailable
) {
}
