package me.kolpa.raspberrymclib.core.usecases;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.SensorInputPin;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.core.usecases.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class GetInputPinInteractor
{
	private UnitOfWorkFactory unitOfWorkFactory;

	public GetInputPinInteractor(UnitOfWorkFactory unitOfWorkFactory)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
	}

	public SensorInputPin getById(int id) throws EntityNotFoundException
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		SensorInputPin inputPin =  unitOfWork.temperatureSensors().getById(id);

		if(inputPin == null)
			throw new EntityNotFoundException();

		return inputPin;

	}

	public List<SensorInputPin> getAll()
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();

		ArrayList<SensorInputPin> sensorInputPins = new ArrayList<>();
		sensorInputPins.addAll(unitOfWork.temperatureSensors().getAll());
		
		return sensorInputPins;
	}
}
