package me.kolpa.raspberryapi.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.kolpa.raspberrymclib.core.model.SensorInputPin;

public class InputPinDto
{
	private int id;
	private int pinNumber;
	private int inputStrength;
	private String sensorName;
	private String value;
	
	public InputPinDto(SensorInputPin sensorInputPin)
	{
		id = sensorInputPin.getPinId();
		pinNumber = sensorInputPin.getPinNumber();
		inputStrength = sensorInputPin.getInputSignalLevel();
		sensorName = sensorInputPin.getName();
		value = sensorInputPin.getValue();
	}

	@JsonProperty("id")
	public int getId()
	{
		return id;
	}

	@JsonProperty("pin_number")
	public int getPinNumber()
	{
		return pinNumber;
	}

	@JsonProperty("input_strength")
	public int getInputStrength()
	{
		return inputStrength;
	}

	@JsonProperty("sensor_type")
	public String getSensorName()
	{
		return sensorName;
	}

	@JsonProperty("input_value")
	public String getValue()
	{
		return value;
	}
}
