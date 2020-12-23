package me.kolpa.raspberrymclib.core.service;

import me.kolpa.raspberrymclib.core.model.SensorInputPin;

import java.util.List;

public interface InputPinService
{
	interface UpdateCallback
	{
		void onUpdate(SensorInputPin inputPin);
	}


	List<SensorInputPin> getAllSensors();
	void setOnUpdate(UpdateCallback callback);
}
