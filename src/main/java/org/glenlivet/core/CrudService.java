package org.glenlivet.core;

import java.util.List;
import java.util.Map;

import org.glenlivet.core.mybatis.PagingBounds;

public interface CrudService<T extends BaseDomain> {
	
	public void addAll(List<T> object);

	public void add(T object);

	public void delete(T object);

	public void update(T object);

	public T getById(String id);

	public List<T> getAll();

	public PagingResult<T> getAll(int offset, int limit);
	
	public List<T> query(Map<String, Object> query);
	
	public PagingResult<T> query(Map<String, Object> query, PagingBounds paging);
	
	public PagingResult<T> query(Map<String,Object> query, int offset, int limit);

}
