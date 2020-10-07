package me.kolpa.raspberrymcspigot;

import me.kolpa.raspberrymclib.core.model.GpioPin;
import me.kolpa.raspberrymcspigot.repository.GpioBlockRepository;
import me.kolpa.raspberrymcspigot.world.GpioBlock;

import java.util.List;

public class RaspberryController
{
	private final Raspberry raspberry;
	private final GpioBlockRepository blocks;
	private List<GpioPin> pins;

	public RaspberryController(Raspberry raspberry, GpioBlockRepository blocks)
	{
		this.raspberry = raspberry;
		this.blocks = blocks;
	}
	
	public void reload()
	{
		blocks.load();
		pins = raspberry.getPins();
	}

	public void addBlock(GpioBlock block)
	{
		for(GpioBlock b : blocks.getAll())
		{
			if(b.getPin() == block.getPin() || b.isSame(block))
			{
				block.getSign().setValid(false);
				return;
			}
		}
		
		for(GpioPin pin : pins)
		{
			if(pin.getPinNumber() != block.getPin())
				continue;

			blocks.add(block);
			updateBlock(block);
			
			blocks.save();
			
			return;
		}
		
		block.getSign().setHasValidPin(false);
	}

	public void removeBlock(GpioBlock block)
	{
		blocks.remove(block);
		blocks.save();
	}

	public void updateBlock(GpioBlock block)
	{
		GpioPin pin = getGpioPinByNumber(block.getPin());
		pin.setInputSignalLevel(block.getBlockPower());
		
		if(pin == null)
			return;
		
		raspberry.updatePin(pin);
		
		blocks.save();
	}
	
	public List<GpioBlock> getBlocks()
	{
		return blocks.getAll();
	}

	private GpioPin getGpioPinByNumber(int pin)
	{
		return pins.stream().filter(x -> x.getPinNumber() == pin).findFirst().orElse(null);
	}
}
