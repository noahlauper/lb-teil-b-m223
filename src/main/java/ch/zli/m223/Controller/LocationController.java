package ch.zli.m223.Controller;

import ch.zli.m223.Model.Location;
import ch.zli.m223.Service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{locationId}")
    public Location getLocationById(
            @PathVariable Long locationId
    ) {
        return locationService.getLocationById(locationId);
    }

    @PostMapping
    public Location createLocation(
            @RequestBody Location location
    ) {
        return locationService.createLocation(location);
    }

    @PutMapping("/{locationId}")
    public Location updateLocation(
            @PathVariable Long locationId,
            @RequestBody Location location
    ) {
        location.setId(locationId);
        return locationService.updateLocation(location);
    }

    @DeleteMapping("/{locationId}")
    public void deleteLocation(
            @PathVariable Long locationId
    ) {
        locationService.deleteLocation(locationId);
    }
}
