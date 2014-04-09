package arduinoMeasurement.view;

import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import arduinoMeasurement.controller.events.ControllerEvent;

public class ControlPanel extends JPanel
{
	private static final long serialVersionUID = -3003134913308139714L;
	final BlockingQueue<ControllerEvent> queue;
	final JButton connectButton;
	
	public ControlPanel(final BlockingQueue<ControllerEvent> queue)
	{
		super();
		this.queue = queue;
		this.setLayout(new MigLayout());
		connectButton = new JButton("Connect");
		this.add(connectButton);
		
	}
	

}
