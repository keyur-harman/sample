package com.kg.template.servicename.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kg.template.servicename.dao.IServiceNameDao;
import com.kg.template.servicename.model.ServiceNameModel;
import com.kg.template.servicename.repository.ServiceNameRepo;

/*
 * This class is used as Java classes for interface that directly access the database
 */
@Component // This annotation marks the Java class as a bean
public class ServiceNameDaoImpl implements IServiceNameDao {

  @Autowired // The @Autowired annotation injects object dependency
  private ServiceNameRepo demoRepo;

  @Override
  public ServiceNameModel save(ServiceNameModel demo) { // To save the modelRequest in the database
    // TODO Auto-generated method stub
    return null;
  }

}
