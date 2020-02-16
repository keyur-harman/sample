package com.prahs.clinical6.servicename.automation.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Database configuration for H2.
 */
@Component
public class DataBaseConfig {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void dataBaseReset() throws SQLException {
    final Connection conn = jdbcTemplate.getDataSource().getConnection();
    try {
      // Add scripts as required
      /*
       * ScriptUtils.executeSqlScript(conn, new ClassPathResource("scripts/drop/create.sql"));
       */
    } catch (final Exception e) {
    } finally {
      conn.close();
    }
  }

}
