package me.kolpa.raspberryapi.impl;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.gpio.*;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;
import com.pi4j.wiringpi.Gpio;
import me.kolpa.raspberrymclib.core.model.PinState;
import me.kolpa.raspberrymclib.impl.service.Raspberry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Pi4JRaspberry implements Raspberry
{
	final GpioController gpio = GpioFactory.getInstance();
	final Collection<GpioPinDigitalOutput> outputs;
	final Collection<GpioPinDigitalInput> inputs;
	List<W1Device> w1Devices;


	public Pi4JRaspberry()
	{
		ArrayList<GpioPinDigitalOutput> outputs = new ArrayList<>();
		outputs.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "GenericPin", com.pi4j.io.gpio.PinState.LOW));
		outputs.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "GenericPin", com.pi4j.io.gpio.PinState.LOW));
		outputs.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "GenericPin", com.pi4j.io.gpio.PinState.LOW));
		outputs.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "GenericPin", com.pi4j.io.gpio.PinState.LOW));
		this.outputs = outputs;
		
		inputs = new ArrayList<>();
		inputs.add(gpio.provisionDigitalInputPin(RaspiPin.GPIO_10, PinPullResistance.PULL_DOWN));

		W1Master master = new W1Master();
		w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
	}

	@Override
	public void updatePin(int pinNumber, PinState newState)
	{
		GpioPinDigitalOutput pin = outputs.stream()
				.filter(gpioPinDigitalOutput -> gpioPinDigitalOutput.getPin()
						.getAddress() == pinNumber)
				.findFirst()
				.get();
		
		if(newState != PinState.High)
		{
			pin.high();
		}
		else
		{
			pin.low();
		}
	}
	
	
	
	@Override
	public int getInput(int pinNumber)
	{
		switch (pinNumber)
		{
			case 7:
				return (int) (((TemperatureSensor) (w1Devices.stream().findFirst().get())).getTemperature());
			case 10:
				return outputs.stream().findFirst().get().getState().getValue();
		}
		
		throw new RuntimeException();
	}
}
//newState == PinState.High ? com.pi4j.io.gpio.PinState.HIGH : com.pi4j.io.gpio.PinState.LOW