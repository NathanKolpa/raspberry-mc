package me.kolpa.raspberrymcspigot.core.repository;

public interface UnitOfWork extends RepositoryCollection
{
	void save();
}
