package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Day;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.mapper.IDaysMapper;
import com.tabisketch.service.IDeleteDayService;
import org.springframework.stereotype.Service;

@Service
public class DeleteDayService implements IDeleteDayService {
    private final IDaysMapper daysMapper;

    public DeleteDayService(final IDaysMapper daysMapper) {
        this.daysMapper = daysMapper;
    }

    @Override
    public void execute(final int id) throws DeleteFailedException {
        final int deleteDayResult = this.daysMapper.deleteById(id);
        if (deleteDayResult != 1) throw new DeleteFailedException(Day.class.getName());
    }
}
