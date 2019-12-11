package com.hansospina.spring.reactive.common;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class Result {

  private HashMap<String, Object> json = new HashMap<>();

  private Result self;

  private boolean success;

  private Result(boolean success) {
    this.self = this;
    this.success = success;
  }

  public static Result withSuccess() {
    return new Result(true);
  }

  public static Result withError(String message) {
    return new Result(false).error(message);
  }

  public static Mono<HashMap<String, Object>> monoWithError(String message) {
    return Mono.just(new Result(false).error(message).json());
  }

  public static Mono<HashMap<String, Object>> monoWithException(Exception ex) {
    return Mono.just(new Result(false).error(ex.getMessage()).json());
  }

  public static Mono<HashMap<String, Object>> monoWithException(Throwable ex) {
    return Mono.just(new Result(false).error(ex.getMessage()).json());
  }

  public static Result withError() {
    return new Result(false);
  }

  public static Result withError(@NotNull Exception ex) {
    return new Result(false).error(ex.getMessage());
  }

  public static Result withError(@NotNull Throwable ex) {
    return new Result(false).error(ex.getMessage());
  }

  public Result merge(Map<String, Object> items) {
    self.json.putAll(items);
    if (items.containsKey("error")) {
      self.success = false;
    }
    return self;
  }

  public Result merge(Result items) {
    return self.merge(items.json());
  }

  public Result set(String key, Object value) {
    self.json.put(key, value);
    return self;
  }

  public Result error(String message) {
    self.json.put("message", message);
    return self;
  }

  public Result withPage(String key, Page page) {
    self.json.put(key, page.getContent());
    self.json.put("total_items", page.getTotalElements());
    self.json.put("total_pages", page.getTotalPages());
    return self;
  }

  public Result withPage(Page page) {
    return withPage("items", page);
  }

  public Result forList(List obj) {
    self.json.put("items", obj);
    return self;
  }

  public HashMap<String, Object> json() {
    json.put("success", isSuccess());
    return json;
  }

  public static Mono<HashMap<String, Object>> just(List items) {
    return Mono.just(Result.withSuccess().set("items", items).json());
  }

  public static Mono<HashMap<String, Object>> just(Object obj) {
    return Mono.just(Result.withSuccess().set("item", obj).json());
  }

  public static Mono<HashMap<String, Object>> just() {
    return Mono.just(Result.withSuccess().json());
  }

  public boolean isSuccess() {
    return success;
  }

}