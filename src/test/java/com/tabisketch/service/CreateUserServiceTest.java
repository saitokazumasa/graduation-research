package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleCreateUserForm;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateUserServiceTest {
    @Autowired
    private ICreateUserService service;
    @MockitoBean
    private IUsersMapper mapper;

    @Test
    public void testExecute() {
        final var user = ExampleUser.gen();
        when(this.mapper.insert(any())).thenReturn(1);
        when(this.mapper.selectById(anyInt())).thenReturn(user);

        final var form = ExampleCreateUserForm.gen();
        this.service.execute(form);

        verify(this.mapper).insert(any());
        verify(this.mapper).selectById(anyInt());
    }
}
