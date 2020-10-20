package me.kolpa.raspberrymcspigot.core.usecases;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymcspigot.core.raspberry.Raspberry;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;

import java.util.List;

public class RedstoneUpdateInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;
	private final Raspberry raspberry;

	public RedstoneUpdateInteractor(UnitOfWorkFactory unitOfWorkFactory, Raspberry raspberry)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
		this.raspberry = raspberry;
	}
	
	public void updateAt(BlockPosition at)
	{
		UnitOfWork unitOfWork = unitOfWorkFactory.create();

		OutputPinStructure outputPinStructure = unitOfWork.outputPinStructures().getByPosition(at);
		
		if(outputPinStructure == null)
			return;
		
		updateSingle(outputPinStructure);
	}
	
	public void updateAll()
	{
		UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		List<OutputPinStructure> structures = unitOfWork.outputPinStructures().getAll();
		
		for(OutputPinStructure structure : structures)
		{
			updateSingle(structure);
		}
	}
	
	
	private void updateSingle(OutputPinStructure structure)
	{
		int oldCurrent = structure.getPower();
		structure.update();

		if(oldCurrent == structure.getPower())
			return;

		OutputPin outputPin = raspberry.getOutputPins()
				.stream()
				.filter(x -> x.getPinNumber() == structure.getPinNumber())
				.findFirst()
				.orElse(null);
		
		if(outputPin == null)
			return;
		
		outputPin.setInputSignalLevel(structure.getPower());
		raspberry.update(outputPin);
	}
}
