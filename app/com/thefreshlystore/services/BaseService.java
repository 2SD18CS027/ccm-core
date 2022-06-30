package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.List;
import com.google.inject.ImplementedBy;

@ImplementedBy(BaseServiceImpl.class)
public interface BaseService<T> {
	public void save(T t);
	public void save(List<T> t);
	public void update(T t);
	public void update(List<T> t);
	public void delete(T t);
	public void delete(List<T> t);
	public T getById(BigInteger id, Boolean isThrowException);
	public T get();
	public List<T> getAll(Integer count, Integer offset, String sortField);
	public List<T> getById(List<BigInteger> id, Integer count, Integer offset);
	public <E> T getByFieldName(String fieldName, E e, Boolean isThrowException);
	public <E> List<T> getByFieldName(String fieldName, E e, Integer count, Integer offset);
	public <E> List<T> getByFieldName(String fieldName, E e, Integer count, Integer offset, String sortField);
	public <E> List<T> getByFieldName(String fieldName, List<E> e, Integer count, Integer offset);
}
