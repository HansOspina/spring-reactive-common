package com.hansospina.spring.reactive.common;


import java.util.Optional;

public interface Box<O, E> {

  static <O, E> Box<O, E> full(final O val) {
    return new FullBox<>(val);
  }


  static <O, E> Box<O, E> other(final E ex) {
    return new UnknownBox<>(ex);
  }

  static <O, E> Box<O, E> empty() {
    return new UnknownBox<>(null);
  }


  public boolean isFull();

  public boolean isOther();

  public boolean isEmpty();

  public Optional<E> getError();

  public Optional<O> getObject();


  public O unpack() throws UnboxException;

  public E popOther() throws UnboxException;

}


