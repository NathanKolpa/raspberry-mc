package me.kolpa.raspberrymclib.impl.service;

import me.kolpa.raspberrymclib.core.model.PinState;

import java.util.Arrays;
import java.util.List;

public class MockRaspberry implements Raspberry
{
	@Override
	public List<Integer> getOutputPins()
	{
		return Arrays.asList(1, 2, 3, 4, 5);
	}

	@Override
	public void updatePin(int pinNumber, PinState newState)
	{
		System.out.println("Pin " + pinNumber + " is now " + newState.toString());
	}
}
