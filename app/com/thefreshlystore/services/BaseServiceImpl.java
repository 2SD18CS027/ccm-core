package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;

import com.thefreshlystore.daos.BaseDAO;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;

public class BaseServiceImpl<T> implements BaseService<T> {
private final BaseDAO<T> baseDAO;
	
	@Inject
	public BaseServiceImpl(BaseDAO<T> baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	@Override
	public void save(T t) {
		baseDAO.save(t);
	}

	@Override
	public void save(List<T> t) {
		baseDAO.save(t);
	}

	@Override
	public void update(T t) {
		baseDAO.update(t);
	}

	@Override
	public void update(List<T> t) {
		baseDAO.update(t);
	}

	@Override
	public void delete(T t) {
		baseDAO.delete(t);
	}

	@Override
	public void delete(List<T> t) {
		baseDAO.delete(t);
	}

	@Override
	public T getById(BigInteger id, Boolean isThrowException) {
		T t = baseDAO.getById(id);
		if(t == null && isThrowException) {
			throw new TheFreshlyStoreException(ApiFailureMessages.ENTITY_NOT_FOUND);
		}
		return t;
	}

	@Override
	public List<T> getById(List<BigInteger> id, Integer count, Integer offset) {
		return baseDAO.getById(id, count, offset);
	}

	@Override
	public <E> T getByFieldName(String fieldName, E e, Boolean isThrowException) {
		T t = baseDAO.getByFieldName(fieldName, e);
		if(t == null && isThrowException) {
			throw new TheFreshlyStoreException(ApiFailureMessages.ENTITY_NOT_FOUND);
		}
		return t;
	}

	@Override
	public <E> List<T> getByFieldName(String fieldName, E e, Integer count, Integer offset) {
		return baseDAO.getByFieldName(fieldName, e, count, offset);
	}

	@Override
	public <E> List<T> getByFieldName(String fieldName, List<E> e, Integer count, Integer offset) {
		return baseDAO.getByFieldName(fieldName, e, count, offset);
	}

	@Override
	public T get() {
		return baseDAO.get();
	}

	@Override
	public List<T> getAll(Integer count, Integer offset, String sortField) {
		return baseDAO.getAll(count, offset, sortField);
	}

	@Override
	public <E> List<T> getByFieldName(String fieldName, E e, Integer count, Integer offset, String sortField) {
		return baseDAO.getByFieldName(fieldName, e, count, offset, sortField);
	}
}
