package me.kolpa.raspberrymcspigot.world;

import org.bukkit.block.Block;

public class GpioBlock extends SavedBlock
{
	public GpioBlock(Block block, PinSign sign, Block signBlock)
	{
		super(block, sign, signBlock);
	}
}
