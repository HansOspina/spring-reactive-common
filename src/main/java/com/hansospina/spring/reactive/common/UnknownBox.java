package com.hansospina.spring.reactive.common;

import java.util.Optional;

public class UnknownBox<O, E> implements Box<O, E> {

  private final E error;


  public UnknownBox(final E ex) {
    super();
    this.error = ex;
  }

  public UnknownBox() {
    super();
    this.error = null;
  }

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public boolean isOther() {
    return this.error != null;
  }

  @Override
  public boolean isEmpty() {
    return this.error == null;
  }

  @Override
  public Optional<E> getError() {
    return Optional.ofNullable(this.error);
  }

  @Override
  public Optional<O> getObject() {
    return Optional.empty();
  }

  @Override
  public O unpack() throws UnboxException {
    throw new UnboxException("There's no object inside the box.");
  }

  @Override
  public E popOther() throws UnboxException {
    return this.error;
  }
}
