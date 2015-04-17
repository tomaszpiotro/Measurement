package arduinoMeasurement.model;

import arduinoMeasurement.mockup.SettingsMockup;
import arduinoMeasurement.transmission.BaudRate;
import arduinoMeasurement.transmission.DataBits;
import arduinoMeasurement.transmission.StopBits;

public class ConnectionSettings
{
	private BaudRate baudRate;
	private DataBits dataBits;
	private StopBits stopBits;
	private boolean parity;
	
	public ConnectionSettings()
	{
		baudRate = BaudRate.b9600;
		dataBits = DataBits.b8;
		stopBits = StopBits.s0;
		parity = false;
	}
	

	void setBaudRate(final BaudRate baudRate)
	{
		this.baudRate = baudRate;
	}
	
	void setDataBits(final DataBits dataBits)
	{
		this.dataBits = dataBits;
	}
	
	void setStopBits(final StopBits stopBits)
	{
		this.stopBits = stopBits;
	}
	
	void setParity(final boolean parity)
	{
		this.parity = parity;
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
	
	public SettingsMockup buildMockup()
	{
		return new SettingsMockup(baudRate, dataBits, stopBits, parity);
	}
	
	public void setSettings(final SettingsMockup settingsMockup)
	{
		baudRate = settingsMockup.getBaudRate();
		dataBits = settingsMockup.getDataBits();
		stopBits = settingsMockup.getStopBits();
		parity = settingsMockup.isParity();
	}

}
