//package com.tabisketch.rest_controller;
//
//import com.tabisketch.bean.form.CreateWaypointListForm;
//import com.tabisketch.bean.view_model.WaypointListViewModel;
//import com.tabisketch.constant.AuthenticationPrincipalExpression;
//import com.tabisketch.exception.InvalidFormException;
//import com.tabisketch.service.ICreateWaypointListService;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/waypoint-list/create")
//public class CreateWaypointListRestController {
//    private final ICreateWaypointListService createWaypointListService;
//
//    public CreateWaypointListRestController(final ICreateWaypointListService createWaypointListService) {
//        this.createWaypointListService = createWaypointListService;
//    }
//
//    @PostMapping
//    public WaypointListViewModel post(
//            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
//            final @Validated CreateWaypointListForm createWaypointListForm,
//            final BindingResult bindingResult
//    ) {
//        if (bindingResult.hasErrors()) throw new InvalidFormException("invalid form");
//
//        return this.createWaypointListService.execute(email, createWaypointListForm);
//    }
//}
