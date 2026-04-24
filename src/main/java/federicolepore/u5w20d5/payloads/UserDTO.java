package federicolepore.u5w20d5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Il nome proprio è obbligatorio, non essere timido, dicci come ti chaimi")
        @Size(min = 2, max = 30, message = "Il nome proprio deve essere compreso tra i 2 e i 30 caratteri")
        String name,

        @NotBlank(message = "Il cognome è obbligatorio, sai quanti omonimi abbiamo?")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra i 2 e i 30 caratteri")
        String surname,

        @NotBlank(message = "L'email è necessaria per la registrazione, altrimenti non possiamo procedere")
        @Email(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
                message = "L'email inserita non è nel formato corretto, inseriscila bene")
        String email,

        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "La password deve contenere almeno 8 caratteri, una lettera maiuscola, una minuscola e un numero")
        String password
) {
}
