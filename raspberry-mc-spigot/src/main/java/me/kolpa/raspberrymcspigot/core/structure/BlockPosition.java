package me.kolpa.raspberrymcspigot.core.structure;

public class BlockPosition
{
	private int x;

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return z;
	}

	public void setZ(int z)
	{
		this.z = z;
	}

	private int y;
	private int z;

	public BlockPosition(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if(obj.getClass() != this.getClass())
			return false;
		
		BlockPosition other = (BlockPosition)obj;
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}
}
