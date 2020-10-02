package me.kolpa.raspberrymcspigot;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener
{
	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		Block attachedBlock = getAttachedBlock(e);

		if (attachedBlock == null)
			return;

		if (attachedBlock.getType() != Material.REDSTONE_LAMP)
			return;

		SignData data = SignData.parse(e.getLines());
		
		if(data == null)
			return;
		
		switch (data.getHeader())
		{
			case "gpio":
				handleGpio(e, data);
				break;
		}
	}

	private void handleGpio(SignChangeEvent event,  SignData signData)
	{
		GpioSign gpioSign = GpioSign.fromData(signData);
		
		String[] lines = gpioSign.createColorText();
		setSignText(event, lines);
	}
	
	private void setSignText(SignChangeEvent event, String[] lines)
	{
		int i = 0;
		for(; i < lines.length; i++)
			event.setLine(i, lines[i]);
		
		for(; i < 4; i++)
			event.setLine(i, "");
	}

	private Block getAttachedBlock(SignChangeEvent e)
	{
		Block sign = e.getBlock();
		BlockData blockData = sign.getBlockData();

		if (!(blockData instanceof Directional))
			return null;

		BlockFace dir = ((Directional) blockData).getFacing();
		return sign.getRelative(dir.getOppositeFace());
	}
}
