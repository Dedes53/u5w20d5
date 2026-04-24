package federicolepore.u5w20d5.controllers;

import federicolepore.u5w20d5.entities.User;
import federicolepore.u5w20d5.exceptions.ValidationException;
import federicolepore.u5w20d5.payloads.LoginDTO;
import federicolepore.u5w20d5.payloads.LoginRespDTO;
import federicolepore.u5w20d5.payloads.NewUserRespDTO;
import federicolepore.u5w20d5.payloads.UserDTO;
import federicolepore.u5w20d5.services.AuthService;
import federicolepore.u5w20d5.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserServices userService;

    public AuthController(AuthService authService, UserServices usersService) {

        this.authService = authService;
        this.userService = usersService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public NewUserRespDTO saveUser(@RequestBody @Validated UserDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        User newU = this.userService.save(body);
        return new NewUserRespDTO(newU.getId());
    }

}
