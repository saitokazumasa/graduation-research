package com.tabisketch.rest_controller;

import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.bean.view_model.WaypointListViewModel;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.exception.InvalidFormException;
import com.tabisketch.service.IEditWaypointListService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/waypoint-list/edit")
public class EditWaypointListRestController {
    private final IEditWaypointListService editWaypointListService;

    public EditWaypointListRestController(final IEditWaypointListService editWaypointListService) {
        this.editWaypointListService = editWaypointListService;
    }

    @PostMapping
    public WaypointListViewModel post(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final @Validated EditWaypointListForm editWaypointListForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) throw new InvalidFormException("invalid form");

        return this.editWaypointListService.execute(email, editWaypointListForm);
    }
}
