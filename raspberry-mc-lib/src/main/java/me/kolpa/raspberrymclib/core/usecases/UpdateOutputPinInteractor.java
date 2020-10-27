package me.kolpa.raspberrymclib.core.usecases;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.core.service.OutputPinService;
import me.kolpa.raspberrymclib.core.usecases.exceptions.EntityNotFoundException;

public class UpdateOutputPinInteractor
{
	private final UnitOfWorkFactory unitOfWorkFactory;
	private final OutputPinService outputPinService;

	public UpdateOutputPinInteractor(UnitOfWorkFactory unitOfWorkFactory, OutputPinService outputPinService)
	{
		this.unitOfWorkFactory = unitOfWorkFactory;
		this.outputPinService = outputPinService;
	}
	
	public OutputPin update(int pinId, int powerLevel) throws EntityNotFoundException
	{
		final UnitOfWork unitOfWork = unitOfWorkFactory.create();
		
		OutputPin outputPin = unitOfWork.outputPins().getById(pinId);
		
		if(outputPin == null)
			throw new EntityNotFoundException();
		
		outputPin.setInputSignalLevel(powerLevel);
		outputPinService.update(outputPin);
		
		unitOfWork.save();
		
		return outputPin;
	}
}
