package arduinoMeasurement.model;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import arduinoMeasurement.common.SortedList;
import arduinoMeasurement.mockup.SingleProbeMockup;

public class ProbesSerie
{
	private final String seriesName;
	private SortedList<Probe> probes;
	private Probe lastInsertedProbe;
	
	public ProbesSerie(final String title)
	{
		this.seriesName = title;
		probes = new SortedList<>();
		lastInsertedProbe = null;
	}

	public String getTitle()
	{
		return seriesName;
	}	
	
	void addProbe(final float value)
	{
		Probe probe = new Probe(value);
		lastInsertedProbe = probe;
		probes.add(probe);
	}
	
	void addProbe(final float value, final Date date)
	{
		Probe probe = new Probe(value, date);
		lastInsertedProbe = probe;
		probes.add(probe);
	}
	
	public SingleProbeMockup buildLastProbeMockup() throws NoLastProbeExeption
	{
		if(lastInsertedProbe != null)
		{
			return new SingleProbeMockup(lastInsertedProbe, seriesName);
		}
		throw new NoLastProbeExeption();
	}
	
	public static ProbesSerie newInstance(final ProbesSerie another)
	{
		final ProbesSerie newInstanceOfMeasurementSerie = new ProbesSerie(another.getTitle());
		newInstanceOfMeasurementSerie.probes = (SortedList<Probe>) Collections.unmodifiableList(another.probes);
		return newInstanceOfMeasurementSerie;
	}

}
