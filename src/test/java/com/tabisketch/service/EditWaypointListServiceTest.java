package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.ExampleWaypointList;
import com.tabisketch.bean.form.ExampleEditWaypointListForm;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.implement.EditWaypointListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EditWaypointListServiceTest {
    @InjectMocks
    private EditWaypointListService editWaypointListService;
    @Mock
    private IWaypointListsMapper waypointListsMapper;

    @Test
    public void testExecute() {
        final var waypointList = ExampleWaypointList.gen();
        when(this.waypointListsMapper.selectByIdAndEmail(anyInt(), anyString())).thenReturn(waypointList);
        when(this.waypointListsMapper.update(any(), anyString())).thenReturn(1);

        final var email = ExampleUser.gen().getEmail();
        final var editWaypointListForm = ExampleEditWaypointListForm.gen();
        final var waypontListViewModel = this.editWaypointListService.execute(email, editWaypointListForm);
        assert waypontListViewModel != null;

        verify(this.waypointListsMapper).selectByIdAndEmail(anyInt(), anyString());
        verify(this.waypointListsMapper).update(any(), anyString());
    }
}
