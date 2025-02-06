package com.tabisketch.api;

import com.tabisketch.bean.form.CreateDayForm;
import com.tabisketch.bean.response.implement.CreateDayResponse;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.service.ICreateDayService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/create-day")
public class CreateDayAPIController {
    private final ICreateDayService createDayService;

    public CreateDayAPIController(final ICreateDayService createDayService) {
        this.createDayService = createDayService;
    }

    @PostMapping
    public CreateDayResponse post(
            final @Validated CreateDayForm createDayForm,
            final BindingResult bindingResult
    ) throws InsertFailedException {
        if (bindingResult.hasErrors()) return CreateDayResponse.failed();

        final int dayId = this.createDayService.execute(createDayForm);
        return CreateDayResponse.success(dayId);
    }
}
