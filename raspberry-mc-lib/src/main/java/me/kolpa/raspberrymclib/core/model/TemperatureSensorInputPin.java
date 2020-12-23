package me.kolpa.raspberrymclib.core.model;

public class TemperatureSensorInputPin extends SensorInputPin
{
	private int temperature;
	private int estimatedMaxTemp, estimatedMinTemp;

	public TemperatureSensorInputPin(int pinNumber, int temperature, int estimatedMaxTemp, int estimatedMinTemp)
	{
		super(pinNumber);
		setEstimatedMaxTemp(estimatedMaxTemp);
		setEstimatedMinTemp(estimatedMinTemp);
		setTemperature(temperature);
	}

	@Override
	public int getInputSignalLevel()
	{
		return ensureRange(Math.round(((float) temperature - (float) estimatedMinTemp) / ((float) estimatedMaxTemp  - (float) estimatedMinTemp) * 15f));
	}

	@Override
	public String getName()
	{
		return "TEMP";
	}

	private int ensureRange(int value)
	{
		return Math.min(Math.max(value, 0), 15);
	}

	public int getTemperature()
	{
		return temperature;
	}

	public void setTemperature(int temperature)
	{
		this.temperature = temperature;
	}

	public int getEstimatedMaxTemp()
	{
		return estimatedMaxTemp;
	}

	public void setEstimatedMaxTemp(int estimatedMaxTemp)
	{
		this.estimatedMaxTemp = estimatedMaxTemp;
	}

	public int getEstimatedMinTemp()
	{
		return estimatedMinTemp;
	}

	public void setEstimatedMinTemp(int estimatedMinTemp)
	{
		this.estimatedMinTemp = estimatedMinTemp;
	}
}
