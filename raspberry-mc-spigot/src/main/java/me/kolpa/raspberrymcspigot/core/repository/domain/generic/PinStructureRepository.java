package me.kolpa.raspberrymcspigot.core.repository.domain.generic;

import me.kolpa.raspberrymcspigot.core.repository.domain.generic.Repository;
import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.PinStructure;

public interface PinStructureRepository<T extends PinStructure> extends Repository<T>
{
	T getByPin(int pin);
	T getByPosition(BlockPosition position);
}
