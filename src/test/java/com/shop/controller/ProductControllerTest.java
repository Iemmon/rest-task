package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.Product;
import com.shop.entity.User;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    public MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void getProductShouldReturnOKWhenProductExists() throws Exception {
        Optional<Product> product = Optional.of(new Product());
        when(productService.getProductById(eq(1), eq(1))).thenReturn(product);

        MvcResult result = mvc.perform(get("/users/1/products/1"))
                .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        Product resultProduct = new ObjectMapper().readValue(json, Product.class);

        assertEquals(product.get(), resultProduct);

        verify(productService).getProductById(1, 1);
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void getProductShouldReturnNOT_FOUNDWhenProductDoesNotExist() throws Exception {
        Optional<Product> product = Optional.empty();
        when(productService.getProductById(eq(1), eq(1))).thenReturn(product);

        mvc.perform(get("/users/1/products/1"))
                .andExpect(status().isNotFound());

        verify(productService).getProductById(1, 1);
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void getAllProductsByUserIdShouldReturnOKWhenUserExists() throws Exception {
        Optional<User> resultUser = Optional.of(new User());
        when(userService.getUserById(eq(1))).thenReturn(resultUser);

        mvc.perform(get("/users/1/products"))
                .andExpect(status().isOk());

        verify(userService).getUserById(eq(1));

    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void getAllProductsByUserIdShouldReturnNOT_FOUNDWhenUserDoesNotExist() throws Exception {
        Optional<User> resultUser = Optional.empty();
        when(userService.getUserById(eq(1))).thenReturn(resultUser);

        mvc.perform(get("/users/1/products"))
                .andExpect(status().isNotFound());

        verify(userService).getUserById(eq(1));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void addProductShouldReturnCREATEDWhenNewProductAdded() throws Exception {
        Product product = new Product();
        when(userService.getUserById(eq(1))).thenReturn(Optional.of(new User()));
        when(productService.addProductToCart(any(Product.class))).thenReturn(product);

        MvcResult result = mvc.perform(post("/users/1/products")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println(json);
        Product resultProduct = new ObjectMapper().readValue(json, Product.class);

        assertEquals(product, resultProduct);
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void deleteProductShouldReturnNO_CONTENTWhenProductDeletedSuccessfully() throws Exception {
        Optional<Product> product = Optional.of(new Product());
        when(productService.getProductById(eq(1), eq(1))).thenReturn(product);

        mvc.perform(delete("/users/1/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(eq(1), eq(1));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void deleteProductShouldReturnNOT_FOUNDWhenProductDoesNotExist() throws Exception {
        Optional<Product> product = Optional.empty();
        when(productService.getProductById(eq(1), eq(1))).thenReturn(product);

        mvc.perform(delete("/users/1/products/1"))
                .andExpect(status().isNotFound());

        verify(productService, never()).deleteProduct(eq(1), eq(1));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void updateProductShouldReturnOKWhenProductIsPresentAndUpdated() throws Exception {
        Product product = new Product();
        product.setId(1);
        when(productService.getProductById(eq(1), eq(1))).thenReturn(Optional.of(product));
        mvc.perform(put("/users/1/products/1")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType("application/json")).andExpect(status().isOk());

        verify(productService).updateProduct(product);
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void updateProductShouldReturnCREATEDWhenProductIsNotPresentButCreated() throws Exception {
        Product product = new Product();

        when(productService.getProductById(eq(1), eq(1))).thenReturn(Optional.empty());
        mvc.perform(put("/users/1/products/1")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType("application/json")).andExpect(status().isCreated());

        verify(productService).updateProduct(product);
    }

}