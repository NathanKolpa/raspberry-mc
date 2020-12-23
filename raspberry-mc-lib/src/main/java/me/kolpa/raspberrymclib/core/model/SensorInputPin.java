package me.kolpa.raspberrymclib.core.model;

public abstract class SensorInputPin extends Pin
{	
	public SensorInputPin(int pinNumber)
	{
		super(pinNumber);
	}
	
	public abstract int getGetInputSignalLevel();
	public abstract String getName();
}
