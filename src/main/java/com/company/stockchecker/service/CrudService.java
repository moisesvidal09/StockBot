package com.company.stockchecker.service;

import java.util.List;

public interface CrudService<T> {

    Long create(T t);

    List<T> getAll();

    T getById(Long id);

    T update(T t);

    void delete(Long id);

}
