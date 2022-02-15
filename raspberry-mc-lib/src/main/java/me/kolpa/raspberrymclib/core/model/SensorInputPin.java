package me.kolpa.raspberrymclib.core.model;

public abstract class SensorInputPin extends Pin
{	
	public SensorInputPin(int pinNumber)
	{
		super(pinNumber);
	}
	
	public abstract int getInputSignalLevel();
	public abstract String getName();
	public abstract String getValue();
}
