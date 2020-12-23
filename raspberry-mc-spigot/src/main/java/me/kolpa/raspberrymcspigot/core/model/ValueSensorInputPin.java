package me.kolpa.raspberrymcspigot.core.model;

import me.kolpa.raspberrymclib.core.model.SensorInputPin;

public class ValueSensorInputPin extends SensorInputPin
{
	private final int inputSignalLevel;
	private final String name;

	public ValueSensorInputPin(int pinNumber, int inputSignalLevel, String name)
	{
		super(pinNumber);
		this.inputSignalLevel = inputSignalLevel;
		this.name = name;
	}

	@Override
	public int getInputSignalLevel()
	{
		return inputSignalLevel;
	}

	@Override
	public String getName()
	{
		return name;
	}
}
