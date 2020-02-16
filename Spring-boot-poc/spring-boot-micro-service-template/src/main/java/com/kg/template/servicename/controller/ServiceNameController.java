package com.kg.template.servicename.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kg.template.servicename.model.ServiceNameModel;
import com.kg.template.servicename.model.request.ServiceNameModelRequest;

/*
 * RestController annotation a convenience annotation annotated with @Controller and @ResponseBody.
 * applied to a class to mark it as a request handler.
 */
@RestController
@RequestMapping("/v1")
public class ServiceNameController {

  /*
   * this request is used to save all the entries in the database.
   */
  @PostMapping
  public ResponseEntity<ServiceNameModel> add(
      @RequestBody ServiceNameModelRequest demoModelRequest) {
    return null;
  }

  /*
   * this request is used to get all the entries from database
   */
  @GetMapping
  public List<ServiceNameModel> getAll() {
    return null;

  }

}
