package com.tabisketch.api;

import com.tabisketch.bean.form.DeletePlaceForm;
import com.tabisketch.bean.response.implement.DeletePlaceResponse;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.service.IDeletePlaceService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delete-place")
public class DeletePlaceAPIController {
    private final IDeletePlaceService deletePlaceService;

    public DeletePlaceAPIController(final IDeletePlaceService deletePlaceService) {
        this.deletePlaceService = deletePlaceService;
    }

    @PostMapping
    public DeletePlaceResponse post(
            final @Validated DeletePlaceForm deletePlaceForm,
            final BindingResult bindingResult
    ) throws DeleteFailedException {
        if (bindingResult.hasErrors()) return DeletePlaceResponse.failed();

        this.deletePlaceService.execute(deletePlaceForm);
        return DeletePlaceResponse.success();
    }
}
