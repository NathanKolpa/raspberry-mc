package me.kolpa.raspberrymclib.core.model;

public class GpioPin extends Pin
{
	private PinState pinState;
	
	public GpioPin(int pinNumber, PinState pinState)
	{
		super(pinNumber);
		setPinState(pinState);
	}

	public PinState getPinState()
	{
		return pinState;
	}

	public void setPinState(PinState pinState)
	{
		this.pinState = pinState;
	}
}
