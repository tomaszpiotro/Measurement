package arduinoMeasurement;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import arduinoMeasurement.controller.Controller;
import arduinoMeasurement.controller.events.ControllerEvent;
import arduinoMeasurement.model.Model;
import arduinoMeasurement.view.View;

public class ArduinoMeasurement
{

	public static void main(String[] args)
	{
		Model model = new Model();
		BlockingQueue<ControllerEvent> queue = new LinkedBlockingQueue<ControllerEvent>();
		View view = new View(queue);
		Controller controller = new Controller(queue, model, view);
		controller.run();
		
	}

}
