package edu.neu.csye6220.models.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserPassword {
    @NotNull
    @Size(min = 6, max = 20, message = "Length of password should within 4 ~ 20")
    private String oldPassword;
    @Size(min = 6, max = 20, message = "Length of password should within 4 ~ 20")
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
