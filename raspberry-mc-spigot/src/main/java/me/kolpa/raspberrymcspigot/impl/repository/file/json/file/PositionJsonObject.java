package me.kolpa.raspberrymcspigot.impl.repository.file.json.file;

import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;

public class PositionJsonObject
{
	public PositionJsonObject(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PositionJsonObject(BlockPosition position)
	{
		x = position.getX();
		y = position.getY();
		z = position.getZ();
	}

	public int x, y, z;
}
