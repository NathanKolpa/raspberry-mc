package me.kolpa.raspberrymcspigot.core.raspberry;

import me.kolpa.raspberrymclib.core.model.OutputPin;

import java.util.List;

public interface Raspberry
{
	List<OutputPin> getOutputPins();
	
	void update(OutputPin pin);
}
