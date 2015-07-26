package org.glenlivet.core;

import java.util.List;

import org.glenlivet.core.mybatis.PagingBounds;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstractCrudServiceImpl<T extends BaseDomain, K extends CrudMapper<T>>
		implements CrudService<T>, ApplicationContextAware {

	private DbIdGenerator idService;

	private K mapper;

	private ApplicationContext context;
	
	public abstract Class<K> getMapperClass();

	@Override
	public void add(T object) {
		object.setId(genId());
		getMapper().add(object);
	}

	@Override
	public void delete(T object) {
		getMapper().delete(object);
	}

	@Override
	public void update(T object) {
		getMapper().update(object);
	}

	@Override
	public T getById(String id) {
		return getMapper().getById(id);
	}

	@Override
	public List<T> getAll() {
		return getMapper().getAll();
	}

	@Override
	public List<T> getAll(int offset, int limit) {
		return getMapper().getAll(new PagingBounds(offset, limit));
	}

	protected String genId() {
		return getIdService().nextId();
	}

	private DbIdGenerator getIdService() {
		if(idService == null){
			synchronized(this){
				if(idService == null){
					idService = context.getBean(DbIdGenerator.class);
				}
			}
		}
		return idService;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	protected K getMapper() {
		if (mapper == null) {
			synchronized (this) {
				if (mapper == null) {
					mapper = getMapperFromSpring();
				}
			}
		}
		return mapper;
	}

	private K getMapperFromSpring() {
		return context.getBean(getMapperClass());
	}

}
