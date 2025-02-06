package com.tabisketch.api;

import com.tabisketch.bean.form.CreatePlaceForm;
import com.tabisketch.bean.response.implement.CreatePlaceResponse;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.service.ICreatePlaceService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/create-place")
public class CreatePlaceAPIController {
    private final ICreatePlaceService createPlaceService;

    public CreatePlaceAPIController(final ICreatePlaceService createPlaceService) {
        this.createPlaceService = createPlaceService;
    }

    @PostMapping
    public CreatePlaceResponse post(
            final @Validated CreatePlaceForm createPlaceForm,
            final BindingResult bindingResult
    ) throws InsertFailedException {
        if (bindingResult.hasErrors()) return CreatePlaceResponse.failed();

        final int placeId = this.createPlaceService.execute(createPlaceForm);
        return CreatePlaceResponse.success(placeId);
    }
}
