package me.kolpa.raspberrymclib.impl.service;

import me.kolpa.raspberrymclib.core.model.PinState;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MockRaspberry implements Raspberry
{
	private final Random rand = new Random();

	@Override
	public void updatePin(int pinNumber, PinState newState)
	{
		System.out.println("Pin " + pinNumber + " is now " + newState.toString());
	}

	@Override
	public int getInput(int pinNumber)
	{
		return rand.nextInt(10) + 20;
	}
}
