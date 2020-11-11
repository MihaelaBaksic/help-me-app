package hr.fer.progi.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import hr.fer.progi.domain.Request;

/**
 * Represents data which user writes when he/she logins to web page.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotNull
    private String username;
    @NotNull
    private String password;

}
