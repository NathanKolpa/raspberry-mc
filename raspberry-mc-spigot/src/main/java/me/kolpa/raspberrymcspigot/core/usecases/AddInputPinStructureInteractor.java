package me.kolpa.raspberrymcspigot.core.usecases;

import me.kolpa.raspberrymcspigot.core.raspberry.Raspberry;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.core.structure.InputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.sign.SignData;

public class AddInputPinStructureInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;
	private final Raspberry raspberry;

	public interface InputPinStructureFactory
	{
		InputPinStructure create(int pin);
	}

	public AddInputPinStructureInteractor(UnitOfWorkFactory unitOfWorkFactory, Raspberry raspberry)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
		this.raspberry = raspberry;
	}

	public void execute(InputPinStructureFactory structureFactory, SignData signData)
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

		InputPinStructure inputPinStructure = structureFactory.create(pin);

		if (!isPinValid || unitOfWork.inputPinStructures().getByPin(pin) != null || raspberry.getInputPins()
				.stream()
				.filter(x -> x.getPinNumber() == inputPinStructure.getPinNumber())
				.findFirst()
				.orElse(null) == null)
			inputPinStructure.setPinValid(false);

		if (inputPinStructure.isValid())
		{
			unitOfWork.inputPinStructures().add(inputPinStructure);
		}

		inputPinStructure.update();

		unitOfWork.save();
	}
}
