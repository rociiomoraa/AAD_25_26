package com.rocio.aad;

public interface Service<T> {

    boolean validate(T entity);

}
