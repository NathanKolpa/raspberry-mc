package me.kolpa.raspberrymcspigot.core.raspberry;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.SensorInputPin;

import java.util.List;

public interface Raspberry
{
	interface InputUpdateHandler
	{
		void onUpdate(SensorInputPin inputPin);
	}
	
	List<OutputPin> getOutputPins();
	void update(OutputPin pin);
	
	List<SensorInputPin> getInputPins();
	void setUpdateHandler(InputUpdateHandler updateHandler);
}
