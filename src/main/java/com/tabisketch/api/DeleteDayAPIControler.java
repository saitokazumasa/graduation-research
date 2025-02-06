package com.tabisketch.api;

import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.service.IDeleteDayService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delete-day/{id}")
public class DeleteDayAPIControler {
    private final IDeleteDayService deleteDayService;

    public DeleteDayAPIControler(final IDeleteDayService deleteDayService) {
        this.deleteDayService = deleteDayService;
    }

    @PostMapping
    public void post(final @PathVariable int id) throws DeleteFailedException {
        this.deleteDayService.execute(id);
    }
}
