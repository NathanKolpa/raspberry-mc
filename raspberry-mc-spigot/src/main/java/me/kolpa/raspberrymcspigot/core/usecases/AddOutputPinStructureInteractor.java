package me.kolpa.raspberrymcspigot.core.usecases;

import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.sign.SignData;

public class AddOutputPinStructureInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;

	public interface OutputPinStructureFactory
	{
		OutputPinStructure create(int pin);
	}

	public AddOutputPinStructureInteractor(UnitOfWorkFactory unitOfWorkFactory)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
	}

	public void execute(OutputPinStructureFactory structureFactory, SignData signData)
	{
		UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		int pin;
		boolean isPinValid = true;

		try
		{
			pin = Integer.parseInt(signData.getData().get("pin"));
		}
		catch (Exception ex)
		{
			pin = -1;
			isPinValid = false;
		}

		OutputPinStructure outputPinStructure = structureFactory.create(pin);
		
		if (!isPinValid || unitOfWork.outputPinStructures().getByPin(pin) != null)
			outputPinStructure.setPinValid(false);

		if (outputPinStructure.isValid())
		{
			unitOfWork.outputPinStructures().add(outputPinStructure);
		}

		outputPinStructure.update();

		unitOfWork.save();
	}
}
