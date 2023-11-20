package ch.zli.m223.Controller;

import ch.zli.m223.Config.FeignClientInterceptor;
import ch.zli.m223.Model.ApplicationUser;
import ch.zli.m223.Service.ApplicationUserService;
import ch.zli.m223.Service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            return this.applicationUserService.getApplicationUsers();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationUser(
            @PathVariable Long id
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            this.applicationUserService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("deleted user with id: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Forbidden");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationUser> applicationUser(
            @PathVariable Long id,
            @RequestBody ApplicationUser applicationUser
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            return ResponseEntity.ok(this.applicationUserService.updateApplicationUser(id, applicationUser));
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }
}
