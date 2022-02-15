package me.kolpa.raspberrymclib.core.model;

public class ButtonSensorInputPin extends SensorInputPin
{
	private boolean isPressed;

	public ButtonSensorInputPin(int pinNumber, boolean isPressed)
	{
		super(pinNumber);
		setPressed(isPressed);
	}

	@Override
	public int getInputSignalLevel()
	{
		return isPressed ? 15 : 0;
	}

	@Override
	public String getName()
	{
		return "BTN";
	}

	@Override
	public String getValue()
	{
		return isPressed ? "ON" : "OFF";
	}

	public void setPressed(boolean pressed)
	{
		isPressed = pressed;
	}
	
	public boolean isPressed()
	{
		return isPressed;
	}
}
