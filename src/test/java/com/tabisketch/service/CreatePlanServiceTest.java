//package com.tabisketch.service;
//
//import com.tabisketch.bean.entity.ExampleUser;
//import com.tabisketch.bean.form.ExampleCreatePlanForm;
//import com.tabisketch.exception.InsertFailedException;
//import com.tabisketch.exception.SelectFailedException;
//import com.tabisketch.mapper.IPlansMapper;
//import com.tabisketch.mapper.IUsersMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class CreatePlanServiceTest {
//    @Autowired
//    private ICreatePlanService createPlanService;
//    @MockitoBean
//    private IUsersMapper usersMapper;
//    @MockitoBean
//    private IPlansMapper plansMapper;
//
//    @Test
//    @WithMockUser
//    public void testExecute() throws InsertFailedException, SelectFailedException {
//        final var user = ExampleUser.generate();
//        when(this.usersMapper.selectByMailAddress(anyString())).thenReturn(user);
//        when(this.plansMapper.insert(any())).thenReturn(1);
//
//        final var createPlanForm = ExampleCreatePlanForm.generate();
//        this.createPlanService.execute(createPlanForm);
//
//        verify(this.usersMapper).selectByMailAddress(anyString());
//        verify(this.plansMapper).insert(any());
//    }
//}
