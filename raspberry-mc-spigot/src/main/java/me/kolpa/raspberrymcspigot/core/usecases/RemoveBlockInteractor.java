package me.kolpa.raspberrymcspigot.core.usecases;

import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.core.repository.domain.generic.PinStructureRepository;
import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.PinStructure;

import java.util.List;

public class RemoveBlockInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;

	public RemoveBlockInteractor(UnitOfWorkFactory unitOfWorkFactory)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
	}

	public void execute(BlockPosition position)
	{
		UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		int removeCount = 0;
		removeCount += removeFromRepository(unitOfWork.outputPinStructures(), position);
		removeCount += removeFromRepository(unitOfWork.inputPinStructures(), position);

		if(removeCount > 0)
			unitOfWork.save();
	}

	// TODO: test the world id
	private <T extends PinStructure> int removeFromRepository(PinStructureRepository<T> pinStructureRepository, BlockPosition position)
	{
		int removeCount = 0;
		List<T> all = pinStructureRepository.getAll();

		for (T structure : all)
		{
			if (structure.containsBlock(position))
			{
				pinStructureRepository.remove(structure);
				removeCount++;
			}
		}

		return removeCount;
	}
}
