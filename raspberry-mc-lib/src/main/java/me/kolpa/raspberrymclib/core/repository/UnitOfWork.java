package me.kolpa.raspberrymclib.core.repository;

import me.kolpa.raspberrymclib.core.repository.domain.GpioPinRepository;

public interface UnitOfWork extends AutoCloseable
{
	GpioPinRepository gpioPins();
}
