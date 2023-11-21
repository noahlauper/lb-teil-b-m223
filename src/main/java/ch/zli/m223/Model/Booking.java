package ch.zli.m223.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @JsonIgnore
    private Location location;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private ApplicationUser applicationUser;

    public Booking(Date date, Period period, Status status) {
        this.date = date;
        this.period = period;
        this.status = status;
        this.location = new Location();
        this.applicationUser = new ApplicationUser();
    }

    public Booking(Date date, Period period, Status status, Location location, ApplicationUser applicationUser) {
        this.date = date;
        this.period = period;
        this.status = status;
        this.location = location;
        this.applicationUser = applicationUser;
    }
}
