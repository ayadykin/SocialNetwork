package com.social.network.rest.dto.profile;

import com.social.network.utils.Constants;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.social.network.utils.Constants.*;

/**
 * Created by Yadykin Andrii on 5/30/2016.
 */
public class PasswordDto {

    private String oldPassword;

    @NotBlank(message = Constants.NOT_BLANK_MESSAGE)
    @Size(min = 6, message = Constants.MIN_PASSWORD_SIZE)
    @Pattern.List({ @Pattern(regexp = "(?=.*\\d).+", message = SECURE_PASSWORD_NO_DIGITS),
            @Pattern(regexp = "(?=.*[a-z]).+", message = SECURE_PASSWORD_LOWER_CASE),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = SECURE_PASWWORD_UPPER_CASE),
            @Pattern(regexp = "(?=.*[!@#$%^&]).+", message = SECURE_PASSWORD_SYMBOLS),
            @Pattern(regexp = "(?=\\S+$).+", message = SECURE_PASSWORD_SPACES) })
    private String newPassword;

    private String confirmPassword;

    public PasswordDto() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}