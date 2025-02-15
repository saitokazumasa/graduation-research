package com.tabisketch.bean.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendVerifyEmailMailFrom {
    @NotBlank
    private String email;
}
