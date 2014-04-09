package arduinoMeasurement.view;

import java.util.concurrent.BlockingQueue;

import arduinoMeasurement.controller.events.ControllerEvent;

public class View 
{
	private final Screen screen;
	
	public View(final BlockingQueue<ControllerEvent> queue)
	{
		this.screen = new Screen(queue);
	}
}
