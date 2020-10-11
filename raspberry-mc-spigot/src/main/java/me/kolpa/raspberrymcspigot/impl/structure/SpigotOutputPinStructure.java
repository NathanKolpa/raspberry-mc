package me.kolpa.raspberrymcspigot.impl.structure;

import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;
import me.kolpa.raspberrymcspigot.impl.text.SpigotTextBuilderFactory;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

public class SpigotOutputPinStructure extends OutputPinStructure
{
	private final Block lampBlock;
	private final Block signBlock;
	private SignChangeEvent changeEvent = null;

	public SpigotOutputPinStructure(int pinNumber, String world, Block lampBlock, Block signBlock)
	{
		super(
				pinNumber,
				world,
				new BlockPosition(lampBlock.getX(), lampBlock.getY(), lampBlock.getZ()),
				new BlockPosition(signBlock.getX(), signBlock.getY(), signBlock.getZ()));
		this.lampBlock = lampBlock;
		this.signBlock = signBlock;
	}

	public Block getLampBlock()
	{
		return lampBlock;
	}

	public Block getSignBlock()
	{
		return signBlock;
	}

	@Override
	public void update()
	{
		updatePower();
		updateText();
	}

	public void updatePower()
	{
		setPower(lampBlock.getBlockPower());
	}

	public void updateText()
	{
		String[] lines = getInfoSign().createText(new SpigotTextBuilderFactory());
		Sign sign = (Sign) signBlock.getState();

		int i = 0;
		for (; i < lines.length; i++)
			sign.setLine(i, lines[i]);

		for (; i < 4; i++)
			sign.setLine(i, "");
		
		sign.update(true);
	}
}
