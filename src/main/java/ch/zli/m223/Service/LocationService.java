package ch.zli.m223.Service;

import ch.zli.m223.Model.Location;
import ch.zli.m223.Repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long locationId) {
        Optional<Location> locationOptional =  locationRepository.findById(locationId);
        return locationOptional.orElse(null);
    }

    @Transactional
    public Location createLocation(Location location) {
        Location finalLocation = new Location(true, true, true, location.getDate());
        return locationRepository.save(finalLocation);
    }

    @Transactional
    public Location updateLocation(Location location) {
        return locationRepository.save(location);
    }

    @Transactional
    public void deleteLocation(Long locationId) {
        Optional<Location> locationOptional = locationRepository.findById(locationId);
        if (locationOptional.isPresent()) {
            locationRepository.deleteById(locationId);
        }
    }
}
