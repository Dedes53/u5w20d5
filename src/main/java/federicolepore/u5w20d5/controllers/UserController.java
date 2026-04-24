package federicolepore.u5w20d5.controllers;

import federicolepore.u5w20d5.entities.User;
import federicolepore.u5w20d5.payloads.UserDTO;
import federicolepore.u5w20d5.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/me")
    public User getUser(@AuthenticationPrincipal User user) {
        return userServices.findById(user.getId());
    }

    @PutMapping("/me")
    public User updateUser(@AuthenticationPrincipal User user,
                           @RequestBody @Valid UserDTO body) {
        return userServices.update(user.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMe(@AuthenticationPrincipal User user) {
        userServices.delete(user);
    }


}
