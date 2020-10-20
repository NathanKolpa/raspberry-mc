package me.kolpa.raspberrymcspigot.core.usecases;

import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.core.repository.domain.generic.PinStructureRepository;
import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.PinStructure;

import java.util.List;

public class RedstoneUpdateInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;

	public RedstoneUpdateInteractor(UnitOfWorkFactory unitOfWorkFactory)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
	}
	
	public void execute(BlockPosition at)
	{
		UnitOfWork unitOfWork = unitOfWorkFactory.create();

		OutputPinStructure outputPinStructure = unitOfWork.outputPinStructures().getByPosition(at);
		
		if(outputPinStructure == null)
			return;
		
		outputPinStructure.update();
	}
}
