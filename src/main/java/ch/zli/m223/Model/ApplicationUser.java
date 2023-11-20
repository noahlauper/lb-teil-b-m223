package ch.zli.m223.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "applicationUser")
    private List<Booking> bookings;

    public ApplicationUser(String surname, String name, String email, String password, String role) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.bookings = new ArrayList<>();
    }

    @Override
    @JsonIgnoreProperties
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnoreProperties
    public String getUsername() {
        return null;
    }

    @Override
    @JsonIgnoreProperties
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnoreProperties
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
