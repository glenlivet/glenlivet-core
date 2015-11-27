package org.glenlivet.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.glenlivet.core.mybatis.PagingBounds;
import org.glenlivet.core.security.SimpleSecurityFilter;
import org.glenlivet.core.security.UserDetails;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

public abstract class AbstractCrudServiceImpl<T extends BaseDomain, K extends CrudMapper<T>>
		implements CrudService<T>, ApplicationContextAware {

	protected DbIdGenerator idService;

	private K mapper;

	private ApplicationContext context;
	
	public abstract Class<K> getMapperClass();
	
	@Override
	public void addAll(List<T> list) {
		UserDetails ud = SimpleSecurityFilter.getCurrentUserDetails();
		for(T o : list){
			Assert.isNull(o.getId());
			o.setId(genId());
			//set createdBy createdTime 
			o.setCreatedBy(ud.getId());
			o.setCreatedTime(new Date());
		}
		getMapper().addAll(list);
	}

	@Override
	public void add(T object) {
		UserDetails ud = SimpleSecurityFilter.getCurrentUserDetails();
		Assert.isNull(object.getId());
		object.setId(genId());
		if(ud != null)
			object.setCreatedBy(ud.getId());
		object.setCreatedTime(new Date());
		getMapper().add(object);
	}

	@Override
	public void delete(T object) {
		getMapper().delete(object);
	}

	@Override
	public void update(T object) {
		Assert.isTrue(object.getId()!=null);
		UserDetails ud = SimpleSecurityFilter.getCurrentUserDetails();
		object.setUpdatedBy(ud.getId());
		object.setUpdatedTime(new Date());
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
	
	@Override
	public List<T> query(Map<String, Object> query) {
		return getMapper().query(query);
	}
	
	@Override
	public List<T> query(Map<String, Object> query, int offset, int limit) {
		return getMapper().query(query, new PagingBounds(offset, limit));
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
