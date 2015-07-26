package org.glenlivet.core;

import java.util.List;

public interface CrudService<T extends BaseDomain> {

	public void add(T object);

	public void delete(T object);

	public void update(T object);

	public T getById(String id);

	public List<T> getAll();

	public List<T> getAll(int offset, int limit);

}
