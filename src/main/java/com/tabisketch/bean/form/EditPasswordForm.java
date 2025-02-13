package com.tabisketch.bean.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditPasswordForm {
    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String newRePassword;

    @AssertTrue(message = "パスワードが一致しません")
    public boolean isMatchPasswordAndRePassword() {
        if (this.newPassword.isBlank()) return false;
        if (this.newRePassword.isBlank()) return false;

        return newPassword.equals(newRePassword);
    }
}
