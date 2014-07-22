package arduinoMeasurement.transmission;

import java.util.LinkedList;

public enum DataBits
{
	b8(8),
	b7(7);
	
	private final int bits;
	
	private DataBits(final int bits)
	{
		this.bits = bits;
	}

	public int getBits()
	{
		return bits;
	}

}
