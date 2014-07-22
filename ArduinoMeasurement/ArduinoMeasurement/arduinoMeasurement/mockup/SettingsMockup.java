package arduinoMeasurement.mockup;

import arduinoMeasurement.transmission.BaudRate;
import arduinoMeasurement.transmission.DataBits;
import arduinoMeasurement.transmission.StopBits;

public class SettingsMockup
{
	private final boolean wireless;
	private final BaudRate baudRate;
	private final DataBits dataBits;
	private final StopBits stopBits;
	private final boolean parity;
	
	public SettingsMockup(final boolean wireless, final BaudRate baudRate, final DataBits dataBits, 
			final StopBits stopBits, final boolean parity)
	{
		this.wireless = wireless;
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
		this.parity = parity;
	}

	public boolean isWireless()
	{
		return wireless;
	}

	public BaudRate getBaudRate()
	{
		return baudRate;
	}

	public DataBits getDataBits()
	{
		return dataBits;
	}

	public StopBits getStopBits()
	{
		return stopBits;
	}

	public boolean isParity()
	{
		return parity;
	}
	
	public String toString()
	{
		return "[wireless: " + wireless + "] [baud rate: " + baudRate + "] [data bits: " + dataBits + "] [stop bits " + 
				stopBits + "] [parity " + parity + "]";
	}
	
}
