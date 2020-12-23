package me.kolpa.raspberrymcspigot.core.structure;

import me.kolpa.raspberrymcspigot.core.structure.sign.InfoSign;
import me.kolpa.raspberrymcspigot.core.structure.sign.SignField;

public abstract class InputPinStructure extends PinStructure
{
	private int power = 0;
	private final SignField powerField = new SignField("0");
	
	private String value = "-";
	private final SignField valueField = new SignField("-");

	private final BlockPosition goldPoisition;
	private final BlockPosition signPosition;
	private final BlockPosition redstonePosition;
	
	public InputPinStructure(int pinNumber, String world, BlockPosition blockPosition, BlockPosition signPosition, BlockPosition redstonePosition)
	{
		super(pinNumber, world, new InfoSign("input"));
		this.goldPoisition = blockPosition;
		this.signPosition = signPosition;
		this.redstonePosition = redstonePosition;

		getInfoSign().addField("power", powerField);
		getInfoSign().addField("value", valueField);
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

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
		valueField.setValue(value);
	}

	public abstract void update();

	public BlockPosition getBlockPosition()
	{
		return goldPoisition;
	}

	public BlockPosition getSignPosition()
	{
		return signPosition;
	}

	public BlockPosition getRedstonePosition()
	{
		return redstonePosition;
	}


	public boolean containsBlock(BlockPosition position)
	{
		return goldPoisition.equals(position) || signPosition.equals(position) || redstonePosition.equals(position);
	}
}
