package me.kolpa.raspberrymclib.impl.service;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.service.OutputPinService;

import java.util.List;
import java.util.stream.Collectors;

public class RaspberryServiceAdapter implements OutputPinService
{
	private final Raspberry raspberry;

	public RaspberryServiceAdapter(Raspberry raspberry)
	{
		this.raspberry = raspberry;
	}

	@Override
	public List<OutputPin> getAll()
	{
		return raspberry.getOutputPins().stream().map(pinId -> new OutputPin(pinId, 0)).collect(Collectors.toList());
	}

	@Override
	public void update(OutputPin pin)
	{
		raspberry.updatePin(pin.getPinNumber(), pin.getPinState());
	}
	
}
