package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.ExampleWaypointList;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.implement.DeleteWaypointListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteWaypointListServiceTest {
    @InjectMocks
    private DeleteWaypointListService deleteWaypointListService;
    @Mock
    private IWaypointListsMapper waypointListsMapper;

    @Test
    public void testExecute() {
        when(this.waypointListsMapper.deleteByIdAndEmail(anyInt(), anyString())).thenReturn(1);

        final var id = ExampleWaypointList.gen().getId();
        final var email = ExampleUser.gen().getEmail();
        this.deleteWaypointListService.execute(id, email);

        verify(this.waypointListsMapper).deleteByIdAndEmail(anyInt(), anyString());
    }
}
