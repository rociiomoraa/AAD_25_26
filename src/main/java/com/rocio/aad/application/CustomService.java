package com.rocio.aad.application;

public interface CustomService<T> {

    boolean validate(T entity);

}
