package ch.zli.m223.Service;

import ch.zli.m223.Model.*;
import ch.zli.m223.Repository.ApplicationUserRepository;
import ch.zli.m223.Repository.BookingRepository;
import ch.zli.m223.Repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final LocationRepository locationRepository;


    @Transactional
    public Booking createBooking(Long locationId, Booking booking, String email) {
        Optional<Location> locationOptional = locationRepository.findById(locationId);
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByEmail(email);
        if (locationOptional.isPresent() && applicationUserOptional.isPresent()) {
            if (booking.getPeriod().equals(Period.FULLDAY) && locationOptional.get().isAvailableOnFullDay()) {
                return updateUserLocationBooking(locationOptional.get(), applicationUserOptional.get(), booking);
            } else if (booking.getPeriod().equals(Period.AFTERNOON) && locationOptional.get().isAvailableAfternoon()){
                return updateUserLocationBooking(locationOptional.get(), applicationUserOptional.get(), booking);
            } else if (booking.getPeriod().equals(Period.MORNING) && locationOptional.get().isAvailableAfternoon()) {
                return updateUserLocationBooking(locationOptional.get(), applicationUserOptional.get(), booking);
            }
        }
        return null;
    }

    private Booking updateUserLocationBooking(Location location, ApplicationUser applicationUser, Booking booking) {
        Booking createdBooking = new Booking(booking.getDate(), booking.getPeriod(), Status.NEW);
        createdBooking.setLocation(location);
        createdBooking.setApplicationUser(applicationUser);
        Booking savedBooking = bookingRepository.save(createdBooking);
        applicationUser.getBookings().add(savedBooking);
        location.getBookings().add(booking);
        location.setAvailableOnFullDay(true);
        location.setAvailableMorning(true);
        location.setAvailableAfternoon(true);
        applicationUserRepository.save(applicationUser);
        locationRepository.save(location);
        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return this.bookingRepository.findAll();
    }

    @Transactional
    public Booking changeBookingStatus(Status status, Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Optional<Location> locationOptional = locationRepository.findById(bookingOptional.get().getLocation().getId());
            if (locationOptional.isPresent()) {
                if (status.equals(Status.ACCEPTED)) {
                    if (bookingOptional.get().getPeriod().equals(Period.FULLDAY)) {
                        return changeStatusToAccepted(bookingOptional.get(), locationOptional.get(), false, false, false);
                    } else if (bookingOptional.get().getPeriod().equals(Period.MORNING)) {
                        return changeStatusToAccepted(bookingOptional.get(), locationOptional.get(), true, false, false);
                    } else if (bookingOptional.get().getPeriod().equals(Period.AFTERNOON)) {
                        return changeStatusToAccepted(bookingOptional.get(), locationOptional.get(), false, true, false);
                    }
                } else {
                    bookingOptional.get().setStatus(Status.REJECTED);
                    return bookingRepository.save(bookingOptional.get());
                }
            }
        }
        return null;
    }

    public Booking changeStatusToAccepted(Booking booking, Location location, boolean isAfternoon, boolean isMorning, boolean isFullDay) {
        booking.setStatus(Status.ACCEPTED);
        Booking savedBooking = bookingRepository.save(booking);
        location.getBookings().add(booking);
        location.setAvailableMorning(isMorning);
        location.setAvailableOnFullDay(isFullDay);
        location.setAvailableAfternoon(isAfternoon);
        locationRepository.save(location);
        return savedBooking;
    }

    public List<Booking> getBookingsFromUser(String email) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByEmail(email);
        return applicationUserOptional.map(ApplicationUser::getBookings).orElse(null);
    }


    public Booking getBookingById(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        return bookingOptional.orElse(null);
    }

    @Transactional
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Transactional
    public void deletePersonalBooking(Long bookingId, String email) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByEmail(email);
        if (applicationUserOptional.isPresent()) {
            for (Booking booking: applicationUserOptional.get().getBookings()) {
                if (Objects.equals(booking.getId(), bookingId)) {
                    this.bookingRepository.deleteById(bookingId);
                }
            }

        }

    }
}
