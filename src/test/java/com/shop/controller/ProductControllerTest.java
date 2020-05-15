package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.Product;
import com.shop.service.ProductService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getProductShouldReturnOKWhenProductExists() throws Exception {
        Optional<Product> product = Optional.of(new Product());
        when(productService.getProductById(anyInt())).thenReturn(product);

        MvcResult result = mvc.perform(get("/productcart/getProduct")
                .param("id", "1"))
                .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        Product resultProduct = new ObjectMapper().readValue(json, Product.class);

        assertEquals(product.get(), resultProduct);
    }

    @Test
    public void getProductShouldReturnNOT_FOUNDWhenProductDoesNotExist() throws Exception {
        Optional<Product> product = Optional.empty();
        when(productService.getProductById(anyInt())).thenReturn(product);

        mvc.perform(get("/productcart/getProduct")
                .param("id", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addProductShouldReturnCREATEDWhenNewProductAdded() throws Exception {
        Product product = new Product();
        when(productService.addProductToCart(product)).thenReturn(product);

        MvcResult result = mvc.perform(post("/productcart/add")
                .content(new ObjectMapper().writeValueAsString(product)).contentType("application/json"))
                .andExpect(status().isCreated()).andReturn();

        String json = result.getResponse().getContentAsString();
        Product resultProduct = new ObjectMapper().readValue(json, Product.class);

        assertEquals(product, resultProduct);
    }

    @Test
    public void deleteProductShouldReturnNO_CONTENTWhenProductDeletedSuccessfully() throws Exception {
        Optional<Product> product = Optional.of(new Product());
        when(productService.getProductById(anyInt())).thenReturn(product);

        mvc.perform(delete("/productcart/delete").param("id", "1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(anyInt());
    }

    @Test
    public void deleteProductShouldReturnNOT_FOUNDWhenProductDoesNotExist() throws Exception {
        Optional<Product> product = Optional.empty();
        when(productService.getProductById(anyInt())).thenReturn(product);

        mvc.perform(delete("/productcart/delete").param("id", "1"))
                .andExpect(status().isNotFound());

        verify(productService, never()).deleteProduct(anyInt());

    }

    @Test
    public void updateProductShouldReturnOKWhenProductIsPresentAndUpdated() throws Exception {
        Product product = new Product();
        product.setId(1);
        when(productService.getProductById(anyInt())).thenReturn(Optional.of(product));
        mvc.perform(put("/productcart/update")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType("application/json")).andExpect(status().isOk());

        verify(productService).updateProduct(product);
    }

    @Test
    public void updateProductShouldReturnCREATEDWhenProductIsNoPresentAndCreated() throws Exception {
        Product product = new Product();

        when(productService.getProductById(anyInt())).thenReturn(Optional.of(product));
        mvc.perform(put("/productcart/update")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType("application/json")).andExpect(status().isCreated());

        verify(productService).updateProduct(product);
    }

}