package com.kg.template.servicename.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kg.template.servicename.model.ServiceNameModel;

/*
 * This interface is used on Java classes that directly access the database annotation works as a
 * marker for any class that fulfills the role of repository or Data Access Object.
 */
@Repository
public interface ServiceNameRepo extends JpaRepository<ServiceNameModel, Long> {

}
