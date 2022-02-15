package me.kolpa.raspberryapi.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.kolpa.raspberrymclib.core.model.OutputPin;

public class GpioPinDto
{
	private int id;
	private String pinState;
	private int pinNumber;
	private int inputStrength;
	
	public GpioPinDto(OutputPin gpioPin)
	{
		id = gpioPin.getPinId();
		pinNumber = gpioPin.getPinNumber();
		inputStrength = gpioPin.getInputSignalLevel();
		
		switch (gpioPin.getPinState())
		{
			case High: pinState = "HIGH"; break;
			case Low: pinState = "LOW"; break;
		}
	}

	@JsonProperty("id")
	public int getId()
	{
		return id;
	}

	@JsonProperty("pin_state")
	public String getPinState()
	{
		return pinState;
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
}
