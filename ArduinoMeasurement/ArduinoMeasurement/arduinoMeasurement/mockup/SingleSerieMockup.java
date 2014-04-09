package arduinoMeasurement.mockup;

import java.util.LinkedList;
import java.util.List;

import arduinoMeasurement.model.ProbesSerie;

public class SingleSerieMockup
{
	private final String title;
	private final List<SingleProbeMockup> probes;
	
	public SingleSerieMockup(final String title, final ProbesSerie probesSerie)
	{
		this.title = title;
		this.probes = new LinkedList<SingleProbeMockup>();
	}

}
