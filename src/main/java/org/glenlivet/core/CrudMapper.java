package org.glenlivet.core;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

public interface CrudMapper<T extends BaseDomain> {
	
	public void addAll(List<T> object);
	
	public void add(T object);
	
	public void delete(T object);
	
	public void update(T object);
	
	public T getById(String id);
	
	public List<T> getAll();
	
	public List<T> getAll(RowBounds rowBounds);

}
