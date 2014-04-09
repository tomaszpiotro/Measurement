package arduinoMeasurement.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * zawiera serie danych
 * 
 * @author ferene
 */
public class SeriesPack
{
	private Map<String, ProbesSerie> series;
	
	public SeriesPack()
	{
		this.series = new HashMap<String, ProbesSerie>();
	}
	
	void addProbe(final float value, final String seriesName)
	{
		if(seriesNameExists(seriesName))
		{
			series.get(seriesName).addProbe(value);
		}
		else
		{
			createNewSerieAndAddProbe(seriesName, value);
		}
	}
	
	void addProbe(final float value, final String seriesName, final Date date)
	{
		if(seriesNameExists(seriesName))
		{
			series.get(seriesName).addProbe(value, date);
		}
		else
		{
			createNewSerieAndAddProbe(seriesName, value, date);
		}
	}
	
	ProbesSerie getProbesSerie(final String seriesName)
	{
		return series.get(seriesName);
	}
	
	private void createNewSerieAndAddProbe(final String seriesName, float value, Date date)
	{
		final ProbesSerie measurementSerie = new ProbesSerie(seriesName);
		measurementSerie.addProbe(value, date);
		series.put(seriesName, measurementSerie);
	}

	private void createNewSerieAndAddProbe(final String seriesName, final float value)
	{
		final ProbesSerie measurementSerie = new ProbesSerie(seriesName);
		measurementSerie.addProbe(value);
		series.put(seriesName, measurementSerie);
	}
	
	
	private boolean seriesNameExists(final String seriesName)
	{
		return series.containsKey(seriesName);
	}
	
	public static SeriesPack newInstance(final SeriesPack another)
	{
		final SeriesPack newInstanceOfSeriesPack = new SeriesPack();
		newInstanceOfSeriesPack.series = Collections.unmodifiableMap(another.series);
		return newInstanceOfSeriesPack;
	}

}
