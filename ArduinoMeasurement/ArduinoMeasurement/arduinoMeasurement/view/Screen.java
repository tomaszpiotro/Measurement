package arduinoMeasurement.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
import arduinoMeasurement.controller.events.ControllerEvent;

public class Screen extends JFrame
{
	private final BlockingQueue<ControllerEvent> queue;
	private final static String WINDOW_NAME = "Arduino Measurement";
	private final static int X_DIMENSION = 800;
	private final static int Y_DIMENSION = 600;
	
	public Screen(final BlockingQueue<ControllerEvent> queue)
	{
		super();
		this.queue = queue;
		setVisible(true);
		setTitle(WINDOW_NAME);
		setSize(X_DIMENSION, Y_DIMENSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new MyPanel());
	}
	
	private class MyPanel extends JPanel
	{
		private JTabbedPane tabbedPanel;
		private final Map<String, GraphPanel> graphsMap;
		private ControlPanel controlPanel;

		
		public MyPanel()
		{
			super();
			setLayout(new BorderLayout());
			tabbedPanel = new JTabbedPane();
			add(tabbedPanel, BorderLayout.CENTER);
			graphsMap = new HashMap<String, GraphPanel>();
			
			addTab("temperature");
			graphsMap.get("temperature").addProbe(700);
			graphsMap.get("temperature").addProbe(701);
			graphsMap.get("temperature").addProbe(702);


			graphsMap.get("temperature").addProbe(703);
			graphsMap.get("temperature").addProbe(704);
			graphsMap.get("temperature").addProbe(705);


			graphsMap.get("temperature").addProbe(706);
			graphsMap.get("temperature").addProbe(707);
			graphsMap.get("temperature").addProbe(708);
			
			controlPanel = new ControlPanel(queue);

			this.add(controlPanel, BorderLayout.NORTH);

			addTab("pressure");
		}
		
		void addTab(final String title)
		{
			if(!graphsMap.containsKey(title))
			{
				GraphPanel graphPanel = new GraphPanel(title);
				graphsMap.put(title, graphPanel);
				tabbedPanel.addTab(title, graphPanel);
			}
		}
	}
	
}
