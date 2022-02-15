package me.kolpa.raspberrymclib.core.usecases;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.SensorInputPin;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.core.service.InputPinService;
import me.kolpa.raspberrymclib.core.usecases.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class GetInputPinInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;
	private final InputPinService inputPinService;
	private boolean hasFetched = false;

	public GetInputPinInteractor(UnitOfWorkFactory unitOfWorkFactory, InputPinService inputPinService)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
		this.inputPinService = inputPinService;
	}

	public SensorInputPin getById(int id) throws EntityNotFoundException
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		if(hasFetched)
		{

			SensorInputPin inputPin = unitOfWork.inputPins().getById(id);

			if (inputPin == null)
				throw new EntityNotFoundException();

			return inputPin;
		}

		unitOfWork.inputPins().addAll(inputPinService.getAllSensors());
		hasFetched = true;

		SensorInputPin inputPin = unitOfWork.inputPins().getById(id);

		if (inputPin == null)
			throw new EntityNotFoundException();

		return inputPin;
	}

	public List<SensorInputPin> getAll()
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();

		if(hasFetched)
		{
			return unitOfWork.inputPins().getAll();
		}

		List<SensorInputPin> pins = inputPinService.getAllSensors();
		unitOfWork.inputPins().addAll(pins);

		hasFetched = true;

		return pins;
	}
}
