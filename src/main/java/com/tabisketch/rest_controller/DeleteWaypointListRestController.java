package com.tabisketch.rest_controller;

import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IDeleteWaypointListService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/waypoint-list/delete/{id}")
public class DeleteWaypointListRestController {
    private final IDeleteWaypointListService deleteWaypointListService;

    public DeleteWaypointListRestController(final IDeleteWaypointListService deleteWaypointListService) {
        this.deleteWaypointListService = deleteWaypointListService;
    }

    @PostMapping
    public void post(
            final @PathVariable int id,
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email
    ) {
        this.deleteWaypointListService.execute(id, email);
    }
}
