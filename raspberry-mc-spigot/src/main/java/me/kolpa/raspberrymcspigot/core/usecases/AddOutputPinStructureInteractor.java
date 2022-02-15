package me.kolpa.raspberrymcspigot.core.usecases;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymcspigot.core.raspberry.Raspberry;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.sign.SignData;

public class AddOutputPinStructureInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;
	private final Raspberry raspberry;

	public interface OutputPinStructureFactory
	{
		OutputPinStructure create(int pin);
	}

	public AddOutputPinStructureInteractor(UnitOfWorkFactory unitOfWorkFactory, Raspberry raspberry)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
		this.raspberry = raspberry;
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

		if (!isPinValid || unitOfWork.outputPinStructures().getByPin(pin) != null || raspberry.getOutputPins()
				.stream()
				.filter(x -> x.getPinNumber() == outputPinStructure.getPinNumber())
				.findFirst()
				.orElse(null) == null)
			outputPinStructure.setPinValid(false);

		if (outputPinStructure.isValid())
		{
			unitOfWork.outputPinStructures().add(outputPinStructure);
		}

		outputPinStructure.update();

		unitOfWork.save();
	}
}
