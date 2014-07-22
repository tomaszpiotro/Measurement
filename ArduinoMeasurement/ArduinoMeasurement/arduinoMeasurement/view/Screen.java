package arduinoMeasurement.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;
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
import arduinoMeasurement.mockup.SingleProbeMockup;

public class Screen extends JFrame
{
	private final BlockingQueue<ControllerEvent> queue;
	private final static String WINDOW_NAME = "Arduino Measurement";
	private final static int X_DIMENSION = 800;
	private final static int Y_DIMENSION = 600;
	private final MainPanel mainPanel;
	
	public Screen(final BlockingQueue<ControllerEvent> queue)
	{
		super();
		this.queue = queue;
		setTitle(WINDOW_NAME);
		setSize(X_DIMENSION, Y_DIMENSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new MainPanel();
		this.add(mainPanel);
		setVisible(true);
	}
	
	public void addSingleProbe(final SingleProbeMockup singleProbeMockup)
	{
		String seriesName = singleProbeMockup.getSeriesName();
		Date date = singleProbeMockup.getDate();
		float value = singleProbeMockup.getValue();
		mainPanel.addNewProbe(seriesName, date, value);
	}
	
	void setConnected(final boolean connected)
	{
		mainPanel.setConnected(connected);
	}
	
	private class MainPanel extends JPanel
	{
		private JTabbedPane tabbedPanel;
		private final Map<String, GraphPanel> graphsMap;
		private ControlPanel controlPanel;

		
		public MainPanel()
		{
			super();
			setLayout(new BorderLayout());
			tabbedPanel = new JTabbedPane();
			add(tabbedPanel, BorderLayout.CENTER);
			controlPanel = new ControlPanel(queue, tabbedPanel);
			add(controlPanel, BorderLayout.NORTH);
			graphsMap = new HashMap<String, GraphPanel>();
		}
		
		void setConnected(final boolean connected)
		{
			controlPanel.setConnected(connected);
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
		
		void addNewProbe(final String seriesName, final Date date, final float value)
		{	
			if(!graphsMap.containsKey(seriesName))
			{
				addTab(seriesName);
			}
			graphsMap.get(seriesName).addProbe(value, date);
			
		}
	}
	
}
