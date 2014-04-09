package arduinoMeasurement.model;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import arduinoMeasurement.common.SortedList;
import arduinoMeasurement.mockup.SingleProbeMockup;

public class ProbesSerie
{
	private final String title;
	private List<Probe> probes;
	
	public ProbesSerie(final String title)
	{
		this.title = title;
		probes = new SortedList<>();
	}

	public String getTitle()
	{
		return title;
	}	
	
	void addProbe(final float value)
	{
		probes.add(new Probe(value));
	}
	
	void addProbe(final float value, final Date date)
	{
		probes.add(new Probe(value, date));
	}
	
	public SingleProbeMockup buildMockup()
	{
		//TODO
		return null;
	}
	
	public static ProbesSerie newInstance(final ProbesSerie another)
	{
		final ProbesSerie newInstanceOfMeasurementSerie = new ProbesSerie(another.getTitle());
		newInstanceOfMeasurementSerie.probes = Collections.unmodifiableList(another.probes);
		return newInstanceOfMeasurementSerie;
	}

}
