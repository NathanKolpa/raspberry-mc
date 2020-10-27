package me.kolpa.raspberryapi.core.repository;

import me.kolpa.raspberryapi.core.repository.domain.OutputPinRepository;

public interface UnitOfWork extends AutoCloseable
{
	OutputPinRepository outputPins();
}
