package me.kolpa.raspberrymcspigot.world;

import org.bukkit.ChatColor;

public class PinSign
{
	public void setValid(boolean valid)
	{
		isValid = valid;
	}

	private boolean isValid = true;
	private boolean hasValidPin = true;
	private int pin;

	public PinSign(int pin)
	{
		this.pin = pin;
	}
	
	public static PinSign fromData(SignData data)
	{
		int pin;
		
		try
		{
			pin = Integer.parseInt(data.getData().get("pin"));
		}
		catch (Exception e)
		{
			PinSign pinSign = new PinSign(-1);
			pinSign.hasValidPin = false;
			return pinSign;
		}
		
		return new PinSign(pin);
	}
	
	public int getPin()
	{
		return pin;
	}
	
	public void setHasValidPin(boolean hasValidPin)
	{
		this.hasValidPin = hasValidPin;
	}

	public String[] createColorText()
	{
		String[] lines = new String[2];
		
		lines[0] = ChatColor.BOLD + "[" + getStatusColor() + "GPIO" + ChatColor.BLACK + "]" + ChatColor.RESET;
		
		if(hasValidPin)
			lines[1] = ChatColor.ITALIC + "pin = " + pin + ChatColor.RESET;
		else 
			lines[1] = ChatColor.BOLD + "" + ChatColor.RED + "pin = INVALID" + ChatColor.RESET;
		
		return lines;
	}
	
	public boolean isValid()
	{
		return hasValidPin && isValid;
	}
	
	private ChatColor getStatusColor()
	{
		if(!isValid())
			return ChatColor.RED;
		
		return ChatColor.GREEN;
	}
}
