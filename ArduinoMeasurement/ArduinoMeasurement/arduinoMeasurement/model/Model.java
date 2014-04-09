package arduinoMeasurement.model;

import java.util.Date;

import arduinoMeasurement.mockup.SingleProbeMockup;

public class Model 
{
	private final SeriesPack seriesPack;
	
	public Model()
	{
		this.seriesPack = new SeriesPack();
	}
	
	public void addProbe(final float value, final String seriesName)
	{
		seriesPack.addProbe(value, seriesName);
	}
	
	public void addProbe(final float value, final String seriesName, final Date date)
	{
		seriesPack.addProbe(value, seriesName, date);
	}
	
	public SingleProbeMockup BuildLastProbeMockup(final String seriesName)
	{
		//TODO
		return null;
		//seriesPack.getProbesSerie(seriesName).
	}
	
	
	
}
