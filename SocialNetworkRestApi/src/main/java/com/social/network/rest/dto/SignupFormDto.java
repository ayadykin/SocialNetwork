package com.social.network.rest.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.social.network.utils.Constants;
import com.social.network.validation.annotations.Password;

public class SignupFormDto {

    @NotBlank(message = Constants.NOT_BLANK_MESSAGE)
    @Email(message = Constants.EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = Constants.NOT_BLANK_MESSAGE)
    @Size(min = 6, message = Constants.MIN_PASSWORD_SIZE)
    @Pattern.List({@Pattern(regexp = "(?=.*\\d).+", message = "Password must contain one digit."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain one upper letter."),
            @Pattern(regexp = "(?=.*[!@#$%^&]).+", message = "Password must contain one special character."),
            @Pattern(regexp = "(?=\\S+$).+", message = "Password must contain no whitespace.")})
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
