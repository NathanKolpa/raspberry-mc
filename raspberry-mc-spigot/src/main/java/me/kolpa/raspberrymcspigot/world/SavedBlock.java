package me.kolpa.raspberrymcspigot.world;

import org.bukkit.block.Block;

public abstract class SavedBlock
{
	public static class BlockPosition
	{
		public int x, y, z;
	}

	private Block block;
	private PinSign sign;
	private Block signBlock;

	public SavedBlock(Block block, PinSign sign, Block signBlock)
	{
		this.block = block;
		this.sign = sign;
		this.signBlock = signBlock;
	}

	public int getPin()
	{
		return sign.getPin();
	}

	boolean isSame(Block block)
	{
		return block.getY() == this.block.getY() && block.getX() == this.block.getX() && block.getZ() == this.block.getZ() && block
				.getType() == this.block.getType();
	}

	public boolean isSame(SavedBlock block)
	{
		return isSame(block.block);
	}

	public boolean isSignSame(Block block)
	{
		return block.getY() == this.signBlock.getY() && block.getX() == this.signBlock.getX() && block.getZ() == this.signBlock
				.getZ() && block.getType() == this.signBlock.getType();
	}

	public PinSign getSign()
	{
		return sign;
	}

	public int getBlockPower()
	{
		return block.getBlockPower();
	}
	
	public String getWorldUuid()
	{
		return block.getWorld().getUID().toString();
	}
	
	public BlockPosition getBlockPosition()
	{
		return mapPosition(block);
	}
	
	public BlockPosition getSignPosition()
	{
		return mapPosition(signBlock);
	}
	
	protected BlockPosition mapPosition(Block block)
	{
		BlockPosition position = new BlockPosition();
		position.x = block.getX();
		position.y = block.getY();
		position.z = block.getZ();
		return position;
	}
}
