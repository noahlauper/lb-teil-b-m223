package ch.zli.m223.Controller;

import ch.zli.m223.Model.ApplicationUser;
import ch.zli.m223.Model.AuthenticationResponse;
import ch.zli.m223.Model.Credential;
import ch.zli.m223.Service.ApplicationUserService;
import ch.zli.m223.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ApplicationUserService applicationUserService;


    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> addUser(
            @RequestBody ApplicationUser applicationUser
    ) {
        return ResponseEntity.ok(applicationUserService.createUser(applicationUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody Credential credential) {
        return ResponseEntity.ok(authenticationService.authenticate(credential));
    }
}
