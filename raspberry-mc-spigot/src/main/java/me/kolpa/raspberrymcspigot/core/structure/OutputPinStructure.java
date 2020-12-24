package me.kolpa.raspberrymcspigot.core.structure;

import me.kolpa.raspberrymcspigot.core.structure.sign.InfoSign;
import me.kolpa.raspberrymcspigot.core.structure.sign.SignField;

public abstract class OutputPinStructure extends PinStructure
{
	private int power = 0;
	private final SignField powerField = new SignField("0");
	
	private final BlockPosition blockPosition;
	private final BlockPosition signPosition;
	
	public OutputPinStructure(int pinNumber, String world, BlockPosition blockPosition, BlockPosition signPosition)
	{
		super(pinNumber, world, new InfoSign("output"));
		this.blockPosition = blockPosition;
		this.signPosition = signPosition;

		getInfoSign().addField("power", powerField);
	}

	public int getPower()
	{
		return power;
	}

	public void setPower(int power)
	{
		this.power = power;
		powerField.setValue(power + "");
	}
	
	public BlockPosition getBlockPosition()
	{
		return blockPosition;
	}

	public BlockPosition getSignPosition()
	{
		return signPosition;
	}
	
	public boolean containsBlock(BlockPosition position)
	{
		return blockPosition.equals(position) || signPosition.equals(position);
	}
}
