package me.kolpa.raspberrymcspigot.world;

import org.bukkit.block.Block;

public class SensorBlock extends SavedBlock
{
	private Block redstoneDust;

	public SensorBlock(Block block, PinSign sign, Block signBlock, Block redstoneDust)
	{
		super(block, sign, signBlock);
		this.redstoneDust = redstoneDust;
	}
	
	public void setRedstoneLevel(int level)
	{
	}
}
