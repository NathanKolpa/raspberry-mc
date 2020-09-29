package me.kolpa.raspberrymclib.impl.repository.inmemory.domain;

import com.sun.jmx.mbeanserver.Repository;
import me.kolpa.raspberrymclib.core.model.GpioPin;
import me.kolpa.raspberrymclib.core.repository.domain.GpioPinRepository;

public class InMemoryGpioRepository extends InMemoryPinRepository<GpioPin> implements GpioPinRepository
{
}
