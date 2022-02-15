package me.kolpa.raspberrymcspigot.core.model;

import me.kolpa.raspberrymclib.core.model.SensorInputPin;

public class ValueSensorInputPin extends SensorInputPin
{
	private int inputSignalLevel;
	private String name;
	private String value;

	public ValueSensorInputPin(int pinNumber, int inputSignalLevel, String name, String value)
	{
		super(pinNumber);
		this.inputSignalLevel = inputSignalLevel;
		this.name = name;
		this.value = value;
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

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void setInputSignalLevel(int inputSignalLevel)
	{
		this.inputSignalLevel = inputSignalLevel;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
