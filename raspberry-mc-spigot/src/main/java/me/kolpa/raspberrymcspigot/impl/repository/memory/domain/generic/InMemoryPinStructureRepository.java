package me.kolpa.raspberrymcspigot.impl.repository.memory.domain.generic;

import me.kolpa.raspberrymcspigot.core.repository.domain.generic.PinStructureRepository;
import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.PinStructure;

public abstract class InMemoryPinStructureRepository<T extends PinStructure> extends InMemoryRepository<T>
		implements PinStructureRepository<T>
{
	@Override
	public T getByPin(int pin)
	{
		return getValues().stream().filter(x -> x.getPinNumber() == pin).findFirst().orElse(null);
	}

	@Override
	public T getByPosition(BlockPosition position)
	{
		return getValues().stream().filter(x -> x.containsBlock(position)).findFirst().orElse(null);
	}
}
