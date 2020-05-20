package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void getUserShouldReturnOKWhenUserExists() throws Exception {
        Optional<User> user = Optional.of(new User());
        when(userService.getUserById(anyInt())).thenReturn(user);

        MvcResult result = mvc.perform(get("/users/1"))
                .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        User resultUser = new ObjectMapper().readValue(json, User.class);

        assertEquals(user.get(), resultUser);
    }

    @Test
    public void getUserShouldReturnNOT_FOUNDWhenUserDoesNotExist() throws Exception {
        Optional<User> user = Optional.empty();
        when(userService.getUserById(anyInt())).thenReturn(user);

        mvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addUserShouldReturnCREATEDWhenNewUserAdded() throws Exception {
        User user = new User();
        when(userService.addNewUser(user)).thenReturn(user);

        MvcResult result = mvc.perform(post("/users/")
                .content(new ObjectMapper().writeValueAsString(user)).contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        String json = result.getResponse().getContentAsString();
        User resultProduct = new ObjectMapper().readValue(json, User.class);

        assertEquals(user, resultProduct);
    }

    @Test
    public void deleteUserShouldReturnNO_CONTENTWhenUserDeletedSuccessfully() throws Exception {
        Optional<User> user = Optional.of(new User());
        when(userService.getUserById(anyInt())).thenReturn(user);

        mvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserById(anyInt());
    }

    @Test
    public void deleteUserShouldReturnNOT_FOUNDWhenUserDoesNotExist() throws Exception {
        Optional<User> user = Optional.empty();
        when(userService.getUserById(anyInt())).thenReturn(user);

        mvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound());

        verify(userService, never()).deleteUserById(anyInt());
    }

    @Test
    public void updateUserShouldReturnOKWhenUserIsPresentAndUpdated() throws Exception {
        User user = new User();
        user.setId(1);
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
        mvc.perform(put("/users/")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType("application/json")).andExpect(status().isOk());

        verify(userService).updateExistingUser(user);
    }

    @Test
    public void updateUserShouldReturnCREATEDWhenUserIsNotPresentButCreated() throws Exception {
        User user = new User();

        when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
        mvc.perform(put("/users/")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType("application/json")).andExpect(status().isCreated());

        verify(userService).updateExistingUser(user);
    }

}
