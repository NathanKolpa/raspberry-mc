package me.kolpa.raspberrymclib.core.model;

public class GpioPin extends Pin
{
	private int inputSignalLevel = 0;
	
	public GpioPin(int pinNumber, int inputSignalLevel)
	{
		super(pinNumber);
		setInputSignalLevel(inputSignalLevel);
	}

	public PinState getPinState()
	{
		if(inputSignalLevel > 0)
			return PinState.High;
		
		return PinState.Low;
	}

	public int getInputSignalLevel()
	{
		return inputSignalLevel;
	}

	public void setInputSignalLevel(int inputSignalLevel)
	{
		if(inputSignalLevel > 15 || inputSignalLevel < 0)
			throw new IllegalArgumentException();
		
		this.inputSignalLevel = inputSignalLevel;
	}
}
