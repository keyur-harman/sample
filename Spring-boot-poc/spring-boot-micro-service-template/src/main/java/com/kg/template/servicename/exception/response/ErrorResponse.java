package com.kg.template.servicename.exception.response;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

  public static class ErrorDetails {

    private String fieldName;

    private String message;

    private LocalDateTime timestamp;

    public String getFieldName() {
      return fieldName;
    }

    public String getMessage() {
      return message;
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
    }

  }

  private List<ErrorDetails> errors;

  public List<ErrorDetails> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorDetails> errors) {
    this.errors = errors;
  }
}