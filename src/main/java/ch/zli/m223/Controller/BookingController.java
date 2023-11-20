package ch.zli.m223.Controller;

import ch.zli.m223.Config.FeignClientInterceptor;
import ch.zli.m223.Model.Booking;
import ch.zli.m223.Model.Status;
import ch.zli.m223.Service.BookingService;
import ch.zli.m223.Service.JwtService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/{locationId}")
    public Booking addBooking(
            @RequestBody Booking booking,
            @PathVariable Long locationId
    ) {
        String jwtToken = FeignClientInterceptor.getBearerTokenHeader().trim().replaceFirst("^Bearer\\s+", "");
        return this.bookingService.createBooking(locationId, booking, jwtService.extractUsername(jwtToken));
    }

    @PutMapping("/{bookingId}/{status}")
    public Booking changeBookingStatus(
            @PathVariable Long bookingId,
            @PathVariable Status status
            ) {
        return this.bookingService.changeBookingStatus(status,bookingId);
    }

}
