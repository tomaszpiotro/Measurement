package arduinoMeasurement.model;

import java.util.Date;

import arduinoMeasurement.mockup.SettingsMockup;
import arduinoMeasurement.mockup.SingleProbeMockup;

public class Model 
{
	private final SeriesPack seriesPack;
	private final ConnectionSettings connsectionSettings;
	
	public Model()
	{
		this.seriesPack = new SeriesPack();
		this.connsectionSettings = new ConnectionSettings();
	}
	
	public void addProbe(final float value, final String seriesName)
	{
		seriesPack.addProbe(value, seriesName);
	}
	
	public void addProbe(final float value, final String seriesName, final Date date)
	{
		seriesPack.addProbe(value, seriesName, date);
	}
	
	public SingleProbeMockup BuildLastProbeMockup(final String seriesName) throws NoLastProbeExeption
	{
		return seriesPack.getProbesSerie(seriesName).buildLastProbeMockup();
	}
	
	public SettingsMockup BuildSettingsMockup()
	{
		return connsectionSettings.buildMockup();
	}
	
	public void changeSettings(final SettingsMockup settingsMockup)
	{
		connsectionSettings.setSettings(settingsMockup);
	}
	
}
