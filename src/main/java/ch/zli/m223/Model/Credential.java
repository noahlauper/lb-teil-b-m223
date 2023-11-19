package ch.zli.m223.Model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credential {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}


