package me.kolpa.raspberrymcspigot.core.structure;

import me.kolpa.raspberrymcspigot.core.structure.sign.InfoSign;
import me.kolpa.raspberrymcspigot.core.structure.sign.SignField;

public abstract class PinStructure
{
	private boolean isPinValid = true;
	private boolean isConnected = true;
	private int pinNumber;
	private String world;
	private final InfoSign infoSign;
	private final SignField pinField;

	public PinStructure(int pinNumber, String world, InfoSign infoSign)
	{
		this.pinNumber = pinNumber;
		this.world = world;
		this.infoSign = infoSign;
		
		pinField = new SignField(pinNumber + "");
		infoSign.addField("pin", pinField);
	}

	public int getPinNumber()
	{
		return pinNumber;
	}

	public void setPinNumber(int pinNumber)
	{
		this.pinNumber = pinNumber;
		pinField.setValue(pinNumber + "");
	}

	public boolean isValid()
	{
		return isPinValid && isConnected;
	}
	
	public boolean isPinValid()
	{
		return isPinValid;
	}

	public void setPinValid(boolean pinValid)
	{
		isPinValid = pinValid;
		pinField.setValid(pinValid);
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	public void setConnected(boolean connected)
	{
		isConnected = connected;
		infoSign.setConnected(connected);
	}
	
	public InfoSign getInfoSign()
	{
		return infoSign;
	}

	public String getWorld()
	{
		return world;
	}

	public void setWorld(String world)
	{
		this.world = world;
	}

	public abstract boolean containsBlock(BlockPosition position);

	public abstract void update();

}
