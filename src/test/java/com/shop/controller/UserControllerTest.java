package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.Product;
import com.shop.entity.User;
import com.shop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void getCustomerShouldReturnOKWhenUserExists() throws Exception {
        Optional<User> customer = Optional.of(new User());
        when(userService.getCustomerById(anyInt())).thenReturn(customer);

        MvcResult result = mvc.perform(get("/user/get")
                .param("id", "1"))
                .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        User resultUser = new ObjectMapper().readValue(json, User.class);

        assertEquals(customer.get(), resultUser);
    }

    @Test
    public void getCustomerShouldReturnNOT_FOUNDWhenUserDoesNotExist() throws Exception {
        Optional<User> user = Optional.empty();
        when(userService.getCustomerById(anyInt())).thenReturn(user);

        mvc.perform(get("/user/get")
                .param("id", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addCustomerShouldReturnCREATEDWhenNewUserAdded() throws Exception {
        User customer = new User();
        when(userService.addNewCustomer(customer)).thenReturn(customer);

        MvcResult result = mvc.perform(post("/user/create")
                .content(new ObjectMapper().writeValueAsString(customer)).contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        String json = result.getResponse().getContentAsString();
        User resultProduct = new ObjectMapper().readValue(json, User.class);

        assertEquals(customer, resultProduct);
    }

    @Test
    public void deleteCustomerShouldReturnNO_CONTENTWhenUserDeletedSuccessfully() throws Exception {
        Optional<User> user = Optional.of(new User());
        when(userService.getCustomerById(anyInt())).thenReturn(user);

        mvc.perform(delete("/user/delete").param("id", "1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteCustomerById(anyInt());
    }

    @Test
    public void deleteCustomerShouldReturnNOT_FOUNDWhenUserDoesNotExist() throws Exception {
        Optional<User> user = Optional.empty();
        when(userService.getCustomerById(anyInt())).thenReturn(user);

        mvc.perform(delete("/user/delete").param("id", "1"))
                .andExpect(status().isNotFound());

        verify(userService, never()).deleteCustomerById(anyInt());

    }

    @Test
    public void updateCustomerShouldReturnOKWhenUserIsPresentAndUpdated() throws Exception {
        User user = new User();
        user.setId(1);
        when(userService.getCustomerById(anyInt())).thenReturn(Optional.of(user));
        mvc.perform(put("/user/update")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType("application/json")).andExpect(status().isOk());

        verify(userService).updateExistingCustomer(user);
    }

    @Test
    public void updateCustomerShouldReturnCREATEDWhenUserIsNotPresentButCreated() throws Exception {
        User user = new User();

        when(userService.getCustomerById(anyInt())).thenReturn(Optional.of(user));
        mvc.perform(put("/user/update")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType("application/json")).andExpect(status().isCreated());

        verify(userService).updateExistingCustomer(user);
    }

}
