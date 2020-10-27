package me.kolpa.raspberrymclib.core.usecases;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.core.service.OutputPinService;
import me.kolpa.raspberrymclib.core.usecases.exceptions.EntityNotFoundException;

import java.util.List;

public class GetOutputPinInteractor
{
	private boolean hasFetched = false;
	private final OutputPinService outputPinService;
	private final UnitOfWorkFactory unitOfWorkFactory;

	public GetOutputPinInteractor(OutputPinService outputPinService, UnitOfWorkFactory unitOfWorkFactory)
	{
		this.outputPinService = outputPinService;
		this.unitOfWorkFactory = unitOfWorkFactory;
	}
	
	public OutputPin getById(int id) throws EntityNotFoundException
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();

		if(hasFetched)
		{
			OutputPin outputPin =  unitOfWork.outputPins().getById(id);

			if(outputPin == null)
				throw new EntityNotFoundException();

			return outputPin;
		}

		unitOfWork.outputPins().addAll(outputPinService.getAll());
		hasFetched = true;
		
		OutputPin outputPin = unitOfWork.outputPins().getById(id);
		
		if(outputPin == null)
			throw new EntityNotFoundException();
		
		return outputPin;
	}
	
	public List<OutputPin> getAll()
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		if(hasFetched)
		{
			return unitOfWork.outputPins().getAll();
		}
		
		List<OutputPin> pins = outputPinService.getAll();
		unitOfWork.outputPins().addAll(pins);
		
		hasFetched = true;
		
		return pins;
	}
}
