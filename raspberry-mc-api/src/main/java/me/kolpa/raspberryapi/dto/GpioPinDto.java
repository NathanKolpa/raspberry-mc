package me.kolpa.raspberryapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.kolpa.raspberrymclib.core.model.GpioPin;

public class GpioPinDto
{
	private int id;
	private String pinState;
	private int pinNumber;
	
	public GpioPinDto(GpioPin gpioPin)
	{
		id = gpioPin.getPinId();
		pinNumber = gpioPin.getPinNumber();
		
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
}
