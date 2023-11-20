package ch.zli.m223.Controller;

import ch.zli.m223.Config.FeignClientInterceptor;
import ch.zli.m223.Model.Booking;
import ch.zli.m223.Model.Status;
import ch.zli.m223.Service.BookingService;
import ch.zli.m223.Service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final JwtService jwtService;


    @GetMapping()
    public List<Booking> getAllBookings() {
        return this.bookingService.getAllBookings();
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(
            @PathVariable Long bookingId
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            return ResponseEntity.ok(bookingService.getBookingById(bookingId));
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }

    @GetMapping("/getPersonalBookings")
    public List<Booking> getPersonalBookings() {
        return this.bookingService.getBookingsFromUser(jwtService.extractUsername(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")));
    }

    @PostMapping("/{locationId}")
    public Booking addBooking(
            @RequestBody Booking booking,
            @PathVariable Long locationId
    ) {
        String jwtToken = FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "");
        return this.bookingService.createBooking(locationId, booking, jwtService.extractUsername(jwtToken));
    }

    @PutMapping("/{bookingId}/{status}")
    public ResponseEntity<Booking> changeBookingStatus(
            @PathVariable Long bookingId,
            @PathVariable Status status
            ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            return ResponseEntity.ok(this.bookingService.changeBookingStatus(status,bookingId));
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(
            @PathVariable Long bookingId
    ) {
        if (jwtService.extractRoleFromJwt(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")).equals("admin")) {
            this.bookingService.deleteBooking(bookingId);
            return ResponseEntity.ok("Removed booking with id: " + bookingId);
        } else {
            return ResponseEntity.status(403).body(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", ""));
        }
    }

    @DeleteMapping("/deletePersonalBooking/{bookingId}")
    public void deletePersonalBooking(
            @PathVariable Long bookingId
    ) {
        this.bookingService.deletePersonalBooking(bookingId, jwtService.extractUsername(FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "")));
    }
}
