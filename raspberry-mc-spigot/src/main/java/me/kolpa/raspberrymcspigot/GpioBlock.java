package me.kolpa.raspberrymcspigot;

import me.kolpa.raspberrymclib.core.model.GpioPin;
import org.bukkit.block.Block;

public class GpioBlock
{
	private GpioPin pin;
	private Block block;
	private GpioSign sign;

	public GpioBlock(GpioPin pin, Block block, GpioSign sign)
	{
		this.pin = pin;
		this.block = block;
		this.sign = sign;
	}
}
