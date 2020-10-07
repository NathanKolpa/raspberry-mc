package me.kolpa.raspberrymcspigot;

import me.kolpa.raspberrymclib.core.model.GpioPin;

import java.util.List;

public interface Raspberry
{
	void updatePin(GpioPin pin);
	List<GpioPin> getPins();
}
