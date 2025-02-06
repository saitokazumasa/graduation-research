package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.MAAToken;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditMailAddressForm {
    @Email
    @NotBlank
    private String currentMailAddress;

    @Email
    @NotBlank
    private String newMailAddress;

    @NotBlank
    private String password;

    public static EditMailAddressForm empty() {
        return new EditMailAddressForm("", "", "");
    }

    public MAAToken toMAAToken(final int userId) {
        return MAAToken.generate(
                userId,
                this.newMailAddress
        );
    }
}
