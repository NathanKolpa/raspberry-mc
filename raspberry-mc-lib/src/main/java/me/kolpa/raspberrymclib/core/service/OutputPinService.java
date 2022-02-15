package me.kolpa.raspberrymclib.core.service;

import me.kolpa.raspberrymclib.core.model.OutputPin;

import java.util.List;

public interface OutputPinService
{
	List<OutputPin> getAllOutputPins();
	void update(OutputPin pin);
}
