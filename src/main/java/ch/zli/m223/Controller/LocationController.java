package ch.zli.m223.Controller;

import ch.zli.m223.Config.FeignClientInterceptor;
import ch.zli.m223.Model.Location;
import ch.zli.m223.Service.JwtService;
import ch.zli.m223.Service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final JwtService jwtService;
    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocationById(
            @PathVariable Long locationId
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            return ResponseEntity.ok(locationService.getLocationById(locationId));
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(
            @RequestBody Location location
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            return ResponseEntity.ok(locationService.createLocation(location));
        } else {
            return ResponseEntity.status(403).body(null);
        }

    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Location> updateLocation(
            @PathVariable Long locationId,
            @RequestBody Location location
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            location.setId(locationId);
            return ResponseEntity.ok(locationService.updateLocation(location));
        } else {
            return ResponseEntity.status(403).body(null);
        }

    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<String> deleteLocation(
            @PathVariable Long locationId
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            locationService.deleteLocation(locationId);
            return ResponseEntity.ok("Location has been deleted");
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }
}
