package me.kolpa.raspberrymclib.core.service;

import me.kolpa.raspberrymclib.core.model.OutputPin;

import java.util.List;

public interface OutputPinService
{
	List<OutputPin> getAll();
	void update(OutputPin pin);
}
