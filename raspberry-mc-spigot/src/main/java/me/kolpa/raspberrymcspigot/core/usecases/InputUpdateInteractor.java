package me.kolpa.raspberrymcspigot.core.usecases;

import me.kolpa.raspberrymclib.core.model.SensorInputPin;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.core.structure.InputPinStructure;

public class InputUpdateInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;

	public InputUpdateInteractor(UnitOfWorkFactory unitOfWorkFactory)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
	}

	public void execute(SensorInputPin inputPin)
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		InputPinStructure pinStructure = unitOfWork.inputPinStructures().getByPin(inputPin.getPinNumber());

		if(pinStructure == null)
			return;
		
		pinStructure.setPower(inputPin.getInputSignalLevel());
		pinStructure.setValue(inputPin.getValue());
		
		pinStructure.update();
	}
}
