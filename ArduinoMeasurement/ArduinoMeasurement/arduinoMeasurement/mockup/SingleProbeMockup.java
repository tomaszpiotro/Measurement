package arduinoMeasurement.mockup;

import java.util.Date;

import arduinoMeasurement.model.Probe;

public class SingleProbeMockup
{
	private final float value;
	private final Date date;
	
	public SingleProbeMockup(final Probe probe)
	{
		this.value = probe.getValue();
		this.date = probe.getDate();
	}

	public float getValue()
	{
		return value;
	}

	public Date getDate()
	{
		return date;
	}
}
