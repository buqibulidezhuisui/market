package com.market.mapper.common;

import java.util.List;

public interface BaseMapper<T> {


    List<T> findAll(T t);

    T findT(T t);

    int saveT(T t);

    int updateT(T t);

    void removeT(T t);


}