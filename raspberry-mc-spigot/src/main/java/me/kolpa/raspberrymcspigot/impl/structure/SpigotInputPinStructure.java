package me.kolpa.raspberrymcspigot.impl.structure;

import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.InputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;
import me.kolpa.raspberrymcspigot.impl.text.SpigotTextBuilderFactory;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.AnaloguePowerable;

public class SpigotInputPinStructure extends InputPinStructure
{
	private final Block lampBlock;
	private final Block signBlock;
	private final Block redstoneBlock;
	
	public SpigotInputPinStructure(int pinNumber, String world, Block lampBlock, Block signBlock, Block redstone)
	{
		super(
				pinNumber,
				world,
				new BlockPosition(lampBlock.getX(), lampBlock.getY(), lampBlock.getZ()),
				new BlockPosition(signBlock.getX(), signBlock.getY(), signBlock.getZ()),
				new BlockPosition(redstone.getX(), redstone.getY(), redstone.getZ()));
		this.lampBlock = lampBlock;
		this.signBlock = signBlock;
		this.redstoneBlock = redstone;
	}
	
	@Override
	public void update()
	{
		updatePower();
		updateText();
	}

	public void updatePower()
	{
		AnaloguePowerable powerable = (AnaloguePowerable)redstoneBlock.getBlockData();
		powerable.setPower(getPower());
		redstoneBlock.setBlockData(powerable);
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
