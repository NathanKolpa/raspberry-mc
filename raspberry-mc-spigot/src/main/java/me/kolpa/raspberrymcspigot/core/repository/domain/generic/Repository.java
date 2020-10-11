package me.kolpa.raspberrymcspigot.core.repository.domain.generic;

import java.util.List;

public interface Repository<T>
{
	void add(T entity);
	List<T> getAll();
	
	void remove(T entity);
}
