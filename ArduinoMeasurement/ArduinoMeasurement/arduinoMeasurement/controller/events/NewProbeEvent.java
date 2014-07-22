package arduinoMeasurement.controller.events;

public class NewProbeEvent extends ControllerEvent
{
	private final String data;
	
	public NewProbeEvent(final String data)
	{
		this.data = data;
	}
	
	public String getData()
	{
		return data;
	}

}
