package arduinoMeasurement.model;

import java.util.Date;

import arduinoMeasurement.mockup.SingleProbeMockup;

/**
 * pojedynczy pomiar
 * 
 * @author ferene
 */
public class Probe implements Comparable<Probe>
{
	final float value;
	final Date date;
	
	public Probe(final float value)
	{
		this.value = value;
		this.date = new Date();
	}
	
	public Probe(final float value, final Date date)
	{
		this.value = value;
		this.date = date;
	}

	@Override
	public int compareTo(Probe another)
	{
		if(this.date.before(another.date) )
		{
			return -1;
		}
		else if(this.date.after(another.date))
		{
			return 1;
		}
		return 0;
	}
	
	public float getValue()
	{
		return value;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	private Probe newInstance(final Probe another)
	{
		return new Probe(value, date);
	}
	
	SingleProbeMockup buildMockup()
	{
		return new SingleProbeMockup(newInstance(this));
	}

}
