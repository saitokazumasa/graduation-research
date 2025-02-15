package com.tabisketch.bean.form;

import com.tabisketch.constant.Transporation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateDeparturePointForm {
    @NotBlank
    private String label;

    @NotBlank
    private String placeId;

    @NotNull
    private LocalDateTime departureDatetime;

    private Transporation transporation;
    private int duration;
}
