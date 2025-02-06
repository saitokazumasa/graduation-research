package com.tabisketch.api;

import com.tabisketch.bean.form.UpdatePlaceForm;
import com.tabisketch.bean.response.implement.UpdatePlaceResponse;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.service.IUpdatePlaceService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/update-place")
public class UpdatePlaceAPIController {
    private final IUpdatePlaceService updatePlaceService;

    public UpdatePlaceAPIController(final IUpdatePlaceService updatePlaceService) {
        this.updatePlaceService = updatePlaceService;
    }

    @PostMapping
    public UpdatePlaceResponse post(
            final @Validated UpdatePlaceForm updatePlaceForm,
            final BindingResult bindingResult
    ) throws UpdateFailedException, InsertFailedException {
        if (bindingResult.hasErrors()) return UpdatePlaceResponse.failed();

        final int placeId = this.updatePlaceService.execute(updatePlaceForm);
        return UpdatePlaceResponse.success(placeId);
    }
}
