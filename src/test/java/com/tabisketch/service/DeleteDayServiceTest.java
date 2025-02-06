package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleDay;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.mapper.IDaysMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DeleteDayServiceTest {
    @MockitoBean
    private IDaysMapper daysMapper;
    @Autowired
    private IDeleteDayService deleteDayService;

    public void testExecute() throws DeleteFailedException {
        final var id = ExampleDay.generate().getId();
        this.deleteDayService.execute(id);

        verify(this.daysMapper).deleteById(anyInt());
    }
}
