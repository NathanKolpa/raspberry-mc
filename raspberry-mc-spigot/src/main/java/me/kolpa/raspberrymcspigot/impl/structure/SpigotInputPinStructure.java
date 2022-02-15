package me.kolpa.raspberrymcspigot.impl.structure;

import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.structure.InputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;
import me.kolpa.raspberrymcspigot.impl.text.SpigotTextBuilderFactory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
		
//		int itemsRequired = (int) Math.max(getPower(), Math.ceil((5 * 64 / 14) * getPower() - 1));
//		System.out.println("Items required: " + itemsRequired);

//		Hopper hopper = (Hopper)redstoneBlock.getState();
//		
//		for (int i = 0; i < 5; i++)
//		{
//			if(itemsRequired > 0)
//			{
//				int stackSize = Math.min(64, itemsRequired);
//				System.out.println("Stacksize: " + stackSize);
//				hopper.getInventory().setItem(i, new ItemStack(Material.VINE, stackSize));
//			}
//			else
//			{
//				hopper.getInventory().clear(i);
//			}
//
//			itemsRequired -= 64;
//		}
//
//		List<Block> relativeBlocks = Arrays.asList(redstoneBlock.getRelative(BlockFace.EAST),
//				redstoneBlock.getRelative(BlockFace.WEST),
//				redstoneBlock.getRelative(BlockFace.NORTH),
//				redstoneBlock.getRelative(BlockFace.SOUTH));
//
//		relativeBlocks = relativeBlocks.stream().filter(Objects::nonNull).collect(Collectors.toList());
//		
//		for (Block block : relativeBlocks)
//		{
//			System.out.println("Updating " + block.getType());
//			block.setBlockData(block.getBlockData());
//		}
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
