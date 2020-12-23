package me.kolpa.raspberrymclib.impl.service;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.SensorInputPin;
import me.kolpa.raspberrymclib.core.model.TemperatureSensorInputPin;
import me.kolpa.raspberrymclib.core.service.InputPinService;
import me.kolpa.raspberrymclib.core.service.OutputPinService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RaspberryServiceAdapter implements OutputPinService, InputPinService
{
	private final Raspberry raspberry;
	private UpdateCallback updateCallback;

	private final List<OutputPin> outputPins = new ArrayList<>();
	private final List<TemperatureSensorInputPin> temperatureSensorInputPins = new ArrayList<>();

	public RaspberryServiceAdapter(Raspberry raspberry)
	{
		this.raspberry = raspberry;
	}

	@Override
	public List<OutputPin> getAllOutputPins()
	{
		return new ArrayList<>(outputPins);
	}

	@Override
	public void update(OutputPin pin)
	{
		raspberry.updatePin(pin.getPinNumber(), pin.getPinState());
	}

	@Override
	public List<SensorInputPin> getAllSensors()
	{
		return new ArrayList<>(temperatureSensorInputPins);
	}

	@Override
	public void setOnUpdate(UpdateCallback callback)
	{
		updateCallback = callback;
	}

	public void pollForUpdates()
	{
		for (TemperatureSensorInputPin sensorInputPin : temperatureSensorInputPins)
		{
			int newValue = raspberry.getInput(sensorInputPin.getPinNumber());
			if (newValue != sensorInputPin.getTemperature())
			{
				sensorInputPin.setTemperature(newValue);

				if (updateCallback != null)
					updateCallback.onUpdate(sensorInputPin);
			}
		}
	}
	
	public void updateAllOutput()
	{
		for(OutputPin outputPin : outputPins)
		{
			update(outputPin);
		}
	}

	public List<OutputPin> getOutputPins()
	{
		return outputPins;
	}

	public List<TemperatureSensorInputPin> getTemperatureSensors()
	{
		return temperatureSensorInputPins;
	}
}
