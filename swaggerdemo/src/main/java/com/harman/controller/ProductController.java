package com.harman.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class ProductController {

    List<String> productList = new ArrayList<>();

    @ApiOperation(value = "View a list of available products",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/list", produces = "application/json")
    public List<String> list(Model model){
        return productList;
    }
    
    @ApiOperation(value = "Search a product with an ID",response = String.class)
    @GetMapping(value = "/show/{id}", produces = "application/json")
    public String showProduct(@PathVariable Integer id, Model model){
        return productList.get(id.intValue());
    }
    @ApiOperation(value = "Add a product")
    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity saveProduct(@RequestBody String product){
        productList.add(product);
        return new ResponseEntity("Product saved successfully", HttpStatus.OK);
    }
    @ApiOperation(value = "Update a product")
    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody String product){
    	productList.remove(id.intValue());
    	productList.add(id, product);
        return new ResponseEntity("Product updated successfully", HttpStatus.OK);
    }
    @ApiOperation(value = "Delete a product")
    @DeleteMapping(value="/delete/{id}", produces = "application/json")
    public ResponseEntity delete(@PathVariable Integer id){
        productList.remove(id.intValue());
        return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
    }
}
