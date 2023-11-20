package ch.zli.m223.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isAvailableMorning;

    private boolean isAvailableAfternoon;

    private boolean isAvailableOnFullDay;

    private Date date;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<Booking> bookings;

    public Location(boolean isAvailableMorning, boolean isAvailableAfternoon, boolean isAvailableOnFullDay, Date date) {
        this.isAvailableMorning = isAvailableMorning;
        this.isAvailableAfternoon = isAvailableAfternoon;
        this.isAvailableOnFullDay = isAvailableOnFullDay;
        this.date = date;
        this.bookings = new ArrayList<>();
    }
}
