package edu.neu.csye6220.models.pojos;

import edu.neu.csye6220.utils.RegexUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserPassword {
    @NotBlank
    @Pattern(regexp = RegexUtil.PASSWORD_PATTERN, message = RegexUtil.INVALID_PASSWORD)
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = RegexUtil.PASSWORD_PATTERN, message = RegexUtil.INVALID_PASSWORD)
    private String newPassword;

    public UserPassword() {
    }

    public UserPassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
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
}
