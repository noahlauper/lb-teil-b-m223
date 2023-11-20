package ch.zli.m223.Controller;

import ch.zli.m223.Config.FeignClientInterceptor;
import ch.zli.m223.Model.ApplicationUser;
import ch.zli.m223.Model.AuthenticationResponse;
import ch.zli.m223.Service.ApplicationUserService;
import ch.zli.m223.Service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application-user")
@AllArgsConstructor
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;
    private final JwtService jwtService;

    @GetMapping()
    public List<ApplicationUser> getApplicationUsers() {
        String jwtToken = FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "");
        String role = jwtService.extractRoleFromJwt(jwtToken);
        System.out.println(role);
        if (role.equals("admin")) {
            return this.applicationUserService.getApplicationUsers();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteApplicationUser(
            @PathVariable Long id
    ) {
        this.applicationUserService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public ApplicationUser applicationUser(
            @PathVariable Long id,
            @RequestBody ApplicationUser applicationUser
    ) {
        return this.applicationUserService.updateApplicationUser(id, applicationUser);
    }
}
