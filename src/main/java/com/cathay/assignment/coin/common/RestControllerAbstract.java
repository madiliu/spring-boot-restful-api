package com.cathay.assignment.coin.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public abstract class RestControllerAbstract {

  @Autowired private HttpServletRequest context;

  public RestControllerAbstract() {}

  public <T> ResponseEntity<RestResults<T>> success(T data) {
    return ResponseEntity.ok(new RestResults(data));
  }

  public <T> ResponseEntity<RestResults<T>> fail(HttpStatus status, String errorMessage) {
    return ResponseEntity.status(status).body(new RestResults(errorMessage));
  }
}
