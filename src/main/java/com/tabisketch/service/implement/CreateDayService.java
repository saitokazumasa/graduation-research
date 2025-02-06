package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Day;
import com.tabisketch.bean.form.CreateDayForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IDaysMapper;
import com.tabisketch.service.ICreateDayService;
import org.springframework.stereotype.Service;

@Service
public class CreateDayService implements ICreateDayService {
    private final IDaysMapper daysMapper;

    public CreateDayService(final IDaysMapper daysMapper) {
        this.daysMapper = daysMapper;
    }

    @Override
    public int execute(final CreateDayForm createDayForm) throws InsertFailedException {
        // dayを追加
        final var day = createDayForm.toDay();
        final int result = this.daysMapper.insert(day);
        if (result != 1) throw new InsertFailedException(Day.class.getName());

        return day.getId();
    }
}
