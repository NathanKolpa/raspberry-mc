package me.kolpa.raspberrymclib.core.repository;

import me.kolpa.raspberrymclib.core.repository.domain.OutputPinRepository;

public interface UnitOfWork extends AutoCloseable
{
	OutputPinRepository outputPins();
}
