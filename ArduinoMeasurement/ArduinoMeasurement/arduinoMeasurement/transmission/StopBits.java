package arduinoMeasurement.transmission;

import java.util.LinkedList;

public enum StopBits
{
	s0(0),
	s1(1);
	
	private final int stopBits;
	
	private StopBits(final int stopBits)
	{
		this.stopBits = stopBits;
	}

	public int getStopBits()
	{
		return stopBits;
	}
}
