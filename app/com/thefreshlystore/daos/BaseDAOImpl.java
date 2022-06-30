package com.thefreshlystore.daos;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;

import io.ebean.Ebean;

public class BaseDAOImpl<T> implements BaseDAO<T> {
	private final String ID;
	private final Class<T> persistentClass;
	
	@Inject
	public BaseDAOImpl(String ID) {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.ID = ID;
	}
	
	@Override
	public void save(T t) {
		Ebean.save(t);
	}

	@Override
	public void save(List<T> t) {
		t.forEach((bean) -> {
			Ebean.save(bean);
		});
	}
	
	@Override
	public void update(T t) {
		Ebean.update(t);
	}

	@Override
	public void update(List<T> t) {
		t.forEach((bean) -> {
			Ebean.update(bean);
		});
	}

	@Override
	public void delete(T t) {
		Ebean.delete(t);
	}

	@Override
	public void delete(List<T> t) {
		t.forEach((bean) -> {
			Ebean.delete(bean);
		});
	}
	
	@Override
	public T getById(BigInteger id) {
		return Ebean.find(persistentClass).where().eq(ID, id).findOne();
	}

	@Override
	public List<T> getById(List<BigInteger> id, Integer count, Integer offset) {
		if(count == null || offset == null) {
			return Ebean.find(persistentClass).where().in(ID, id).findList();
		}
		return Ebean.find(persistentClass).where().in(ID, id).setFirstRow(offset).setMaxRows(count).findList();
	}

	@Override
	public <E> T getByFieldName(String fieldName, E e) {
		return Ebean.find(persistentClass).where().eq(fieldName, e).findOne();
	}

	@Override
	public <E> List<T> getByFieldName(String fieldName, E e, Integer count, Integer offset) {
		if(count == null || offset == null) {
			return Ebean.find(persistentClass).where().eq(fieldName, e).findList();
		}
		return Ebean.find(persistentClass).where().eq(fieldName, e).setFirstRow(offset).setMaxRows(count).findList();
	}

	@Override
	public <E> List<T> getByFieldName(String fieldName, List<E> e, Integer count, Integer offset) {
		if(count == null || offset == null) {
			return Ebean.find(persistentClass).where().in(fieldName, e).findList();
		}
		return Ebean.find(persistentClass).where().in(fieldName, e).setFirstRow(offset).setMaxRows(count).findList();
	}

	@Override
	public T get() {
		return Ebean.find(persistentClass).findOne();
	}

	@Override
	public List<T> getAll(Integer count, Integer offset, String sortField) {
		if(count == null || offset == null) {
			return Ebean.find(persistentClass).findList();
		}
		return Ebean.find(persistentClass).order(sortField).setFirstRow(offset).setMaxRows(count).findList();
	}

	@Override
	public <E> List<T> getByFieldName(String fieldName, E e, Integer count, Integer offset, String sortField) {
		if(count == null || offset == null) {
			return Ebean.find(persistentClass).where().eq(fieldName, e).order(sortField).findList();
		}
		return Ebean.find(persistentClass).where().eq(fieldName, e).order(sortField).setFirstRow(offset).setMaxRows(count).findList();
	}
}
