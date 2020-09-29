package me.kolpa.raspberrymclib.core.repository.domain;

import java.util.List;

public interface Repository<T>
{
	List<T> getAll();
	T getById(int id);
	void add(T v);
}
