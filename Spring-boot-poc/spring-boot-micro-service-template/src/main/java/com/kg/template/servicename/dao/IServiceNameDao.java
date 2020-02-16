package com.kg.template.servicename.dao;

import org.springframework.stereotype.Component;

import com.kg.template.servicename.model.ServiceNameModel;

/*
 * This interface is used on Java classes that directly access the database
 */
@Component
public interface IServiceNameDao {

  ServiceNameModel save(ServiceNameModel demo);
}
