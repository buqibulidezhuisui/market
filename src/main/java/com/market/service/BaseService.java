package com.market.service;


import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface BaseService<T>{

	int saveT(T t);

	PageInfo<T> findAll(T t, int pageNum);

    T findT(T t);

    void removeT(T t);


}
