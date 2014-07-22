package arduinoMeasurement.mockup;

import java.util.Date;

import arduinoMeasurement.model.Probe;

public class SingleProbeMockup
{
	private final float value;
	private final Date date;
	private final String seriesName;
	
	public SingleProbeMockup(final Probe probe, final String seriesName)
	{
		this.value = probe.getValue();
		this.date = probe.getDate();
		this.seriesName = seriesName;
	}

	public float getValue()
	{
		return value;
	}

	public Date getDate()
	{
		return date;
	}

	public String getSeriesName()
	{
		return seriesName;
	}
}
