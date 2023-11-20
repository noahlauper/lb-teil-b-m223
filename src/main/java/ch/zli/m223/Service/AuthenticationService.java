package ch.zli.m223.Service;


import ch.zli.m223.Model.ApplicationUser;
import ch.zli.m223.Model.AuthenticationResponse;
import ch.zli.m223.Model.Credential;
import ch.zli.m223.Repository.ApplicationUserRepository;
import io.jsonwebtoken.Jwt;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private ApplicationUserRepository applicationUserRepository;
    private JwtService jwtService;

    public AuthenticationResponse authenticate(Credential credential) {
        Optional<ApplicationUser> applicationUserOptional = this.applicationUserRepository.findByEmail(credential.getEmail());
        try {
            if (applicationUserOptional.isPresent() && applicationUserOptional.get().getPassword().equals(credential.getPassword())) {
                Map<String, Object> extraClaims = new HashMap<>();
                    extraClaims.put("role", applicationUserOptional.get().getRole());
                    String token = jwtService.generateToken(extraClaims, credential);
                    return new AuthenticationResponse(token);
            }
        } catch (Exception e) {
            System.err.println("Couldnt validate password");
        }
        return null;
    }
}
