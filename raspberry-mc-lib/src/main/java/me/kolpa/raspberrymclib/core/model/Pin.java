package me.kolpa.raspberrymclib.core.model;

public abstract class Pin
{
	private int pinId;
	private int pinNumber;

	public Pin(int pinNumber)
	{
		setPinNumber(pinNumber);
	}
	
	public Pin(int pinId, int pinNumber)
	{
		setPinNumber(pinNumber);
		setPinId(pinId);
	}

	public int getPinId()
	{
		return pinId;
	}

	public void setPinId(int pinId)
	{
		this.pinId = pinId;
	}

	public int getPinNumber()
	{
		return pinNumber;
	}

	public void setPinNumber(int pinNumber)
	{
		this.pinNumber = pinNumber;
	}
}
