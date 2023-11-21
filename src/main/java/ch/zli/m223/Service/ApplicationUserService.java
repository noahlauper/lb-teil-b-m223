package ch.zli.m223.Service;

import ch.zli.m223.Model.ApplicationUser;
import ch.zli.m223.Repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUser createUser(ApplicationUser applicationUser) {
        if (applicationUserRepository.count() > 0) {
            ApplicationUser finalApplicationUser = new ApplicationUser(applicationUser.getSurname(), applicationUser.getName(), applicationUser.getEmail(), applicationUser.getPassword(), "member");
            return this.applicationUserRepository.save(finalApplicationUser);

        } else {
            ApplicationUser finalApplicationUser = new ApplicationUser(applicationUser.getSurname(), applicationUser.getName(), applicationUser.getEmail(), applicationUser.getPassword(), "admin");
            return this.applicationUserRepository.save(finalApplicationUser);
        }
    }

    public List<ApplicationUser> getApplicationUsers() {
        return this.applicationUserRepository.findAll();
    }

    public void deleteUser(Long id) {
        this.applicationUserRepository.deleteById(id);
    }

    public ApplicationUser updateApplicationUser(Long id,ApplicationUser applicationUser) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(id);
        if (applicationUserOptional.isPresent()) {
            applicationUserOptional.get().setName(applicationUser.getName());
            applicationUserOptional.get().setEmail(applicationUser.getEmail());
            applicationUserOptional.get().setSurname(applicationUser.getSurname());
            applicationUserOptional.get().setBookings(applicationUser.getBookings());
            applicationUserOptional.get().setPassword(applicationUser.getPassword());
            applicationUserOptional.get().setRole(applicationUser.getRole());
            return this.applicationUserRepository.save(applicationUserOptional.get());
        }
        return null;
    }
}
