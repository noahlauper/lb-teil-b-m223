package ch.zli.m223.Config;

import ch.zli.m223.Model.*;
import ch.zli.m223.Repository.ApplicationUserRepository;
import ch.zli.m223.Repository.BookingRepository;
import ch.zli.m223.Repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
@AllArgsConstructor
public class DataConfig {

    @Bean
    CommandLineRunner commandLineRunner(BookingRepository bookingRepository, LocationRepository locationRepository, ApplicationUserRepository applicationUserRepository) {
        return args -> {
            Location location1 = new Location(
                    "dubai",
                    true,
                    true,
                    true,
                    new Date()
            );

            Location location2 = new Location(
                    "bern",
                    true,
                    false,
                    false,
                    new Date()
            );


            Location location3 = new Location(
                    "zürich",
                    false,
                    true,
                    false,
                    new Date()
            );


            locationRepository.saveAll(
                    List.of(location1, location2, location3)
            );


            ApplicationUser applicationUser = new ApplicationUser(
                   "hans",
                   "peter",
                   "hanspete2r@gmail.com",
                   "1234",
                   "admin"
            );

            ApplicationUser applicationUser2 = new ApplicationUser(
                   "max",
                   "mustermann",
                   "maxiMusti@gmail.com",
                   "1234",
                   "member"
            );

            applicationUserRepository.save(applicationUser);
            applicationUserRepository.save(applicationUser2);

            Booking booking = new Booking(
                    new Date(),
                    Period.AFTERNOON,
                    Status.NEW,
                    location2,
                    applicationUser2
            );

            Booking booking2 = new Booking(
                    new Date(),
                    Period.AFTERNOON,
                    Status.NEW,
                    location3,
                    applicationUser
            );

            bookingRepository.saveAll(
                    List.of(booking, booking2)
            );
        };
    }

}
