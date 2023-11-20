package ch.zli.m223.Model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.control.CodeGenerationHint;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    @NotNull
    private String token;
}
