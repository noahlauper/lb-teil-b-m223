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
        return this.applicationUserRepository.save(applicationUser);
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
            ApplicationUser applicationUser1 = applicationUserOptional.get();
            applicationUser1 = applicationUser;
            return this.applicationUserRepository.save(applicationUser1);
        }
        return null;
    }
}
