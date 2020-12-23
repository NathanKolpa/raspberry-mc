package me.kolpa.raspberrymclib.impl.service;

import me.kolpa.raspberrymclib.core.model.PinState;

import java.util.List;

public interface Raspberry
{
	void updatePin(int pinNumber, PinState newState);
	int getInput(int pinNumber);
}
