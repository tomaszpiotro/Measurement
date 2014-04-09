package arduinoMeasurement.mockup;

import arduinoMeasurement.model.SeriesPack;

public class AllSeriesMockup
{
	private final SeriesPack seriesPack;
	
	public AllSeriesMockup(final SeriesPack seriesPack)
	{
		this.seriesPack = SeriesPack.newInstance(seriesPack);
	}
	
	public SeriesPack getSeriesPack()
	{
		return seriesPack;
	}

}
