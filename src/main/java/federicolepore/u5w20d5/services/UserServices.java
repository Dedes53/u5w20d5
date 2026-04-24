package federicolepore.u5w20d5.services;

import federicolepore.u5w20d5.entities.User;
import federicolepore.u5w20d5.exceptions.BadRequestException;
import federicolepore.u5w20d5.payloads.UserDTO;
import federicolepore.u5w20d5.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder bcrypt;

    public UserServices(UserRepository userRepository, PasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    public User save(UserDTO body) {
        if (this.userRepository.existsByEmail(body.email()))
            throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso ed associato ad un altro utente!");

        User newU = new User(body.name(), body.surname(), body.email(), this.bcrypt.encode(body.password()), body.role());
        User savedU = this.userRepository.save(newU);

        log.info("L'utente con id " + savedU.getId() + " è stato salvato correttamente!");

        return savedU;
    }


}
