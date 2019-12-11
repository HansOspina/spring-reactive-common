package com.hansospina.spring.reactive.common;

import java.util.Optional;

public class FullBox<O, E> implements Box<O, E> {

  private final O object;


  FullBox(final O obj) {
    super();
    this.object = obj;
  }

  @Override
  public boolean isFull() {
    return true;
  }

  @Override
  public boolean isOther() {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return object == null;
  }

  @Override
  public O unpack() {
    return this.object;
  }


  @Override
  public E popOther() throws UnboxException {
    throw new UnboxException("There's no other item inside the box.");
  }

  @Override
  public Optional<E> getError() {
    return Optional.empty();
  }

  @Override
  public Optional<O> getObject() {
    return Optional.of(object);
  }
}
