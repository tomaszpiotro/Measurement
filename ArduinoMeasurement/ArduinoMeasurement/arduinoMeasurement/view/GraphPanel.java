package arduinoMeasurement.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

public class GraphPanel extends JPanel
{
	private static final long serialVersionUID = 1751611887848483444L;
	private final String title;
	private TimeSeriesCollection timeSeries;
	private ChartPanel chartPanel;
	private JFreeChart basicChart;
	private final JPanel controlPanel;
	private final static int H_GAP = 10;
	private final static int V_GAP = 10;
	private JComboBox<String> lineRenderOptionsChooser;
	private String[] lineRenderOptions = {"XY Spline", "another"};
	private JComboBox<String> timeSeriesChooser;
	
	
	public GraphPanel(final String title)
	{
		this.title = title;
		this.timeSeries = new TimeSeriesCollection();
		timeSeries.addSeries(new TimeSeries(title));
		setLayout(new BorderLayout());
		basicChart = createChart(title);
		chartPanel = new ChartPanel(basicChart);
		add(chartPanel, BorderLayout.CENTER);
		this.controlPanel = new JPanel(new GridLayout(16, 2, H_GAP, V_GAP));
		add(controlPanel, BorderLayout.EAST);
		lineRenderOptionsChooser = new JComboBox<String>(lineRenderOptions);
		lineRenderOptionsChooser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String selectedOption = (String)lineRenderOptionsChooser.getSelectedItem();
				switch(selectedOption)
				{
					case "XY Spline":
						basicChart.getXYPlot().setRenderer(new XYSplineRenderer());
						basicChart.getXYPlot().getRenderer().setSeriesPaint(getSelectedSeriesIndex(), Color.BLUE);
						basicChart.getXYPlot().getRenderer().setSeriesStroke(0, 
							    new BasicStroke(
							            2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,
							            1.0f, new float[] {6.0f, 6.0f}, 0.0f));
						break;
					case "another":
						basicChart.getXYPlot().setRenderer(new StandardXYItemRenderer(6));
				}
			}
		});
		controlPanel.add(new JLabel("ustaw typ wykresu"));
		controlPanel.add(lineRenderOptionsChooser);
		
		controlPanel.add(new JLabel("Wybierz serie:"));
		timeSeriesChooser = makeSeriesNamesComboBox();
		controlPanel.add(timeSeriesChooser);
	}

	private JComboBox<String> makeSeriesNamesComboBox()
	{
		List<String> timeSeriesNames = new LinkedList<>();
		for(Object times: timeSeries.getSeries())
		{
			timeSeriesNames.add((String) ((TimeSeries)times).getKey());
		}		
		String[] timeSeriesNamesArray = (String[]) timeSeriesNames.toArray(new String[0]);

		return new JComboBox<String>(timeSeriesNamesArray);
	}
	
	private int getSelectedSeriesIndex()
	{
		String selectedName = (String)timeSeriesChooser.getSelectedItem();
		TimeSeries ts = timeSeries.getSeries(selectedName);
		int index = timeSeries.getSeries().indexOf(ts);
		
		return index;
	}
	
	private JFreeChart createChart(final String title) 
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Time", title, timeSeries);
		//chart.getXYPlot().setRenderer(new XYSplineRenderer());
        return chart;
    }
	
	public String getTitle()
	{
		return title;
	}
	
	void addProbe(final float value)
	{
		timeSeries.getSeries(0).addOrUpdate(new TimeSeriesDataItem(new Millisecond(new Date()), value));
	}
	
	void addProbe(final float value, final Date date)
	{
		timeSeries.getSeries(0).addOrUpdate(new TimeSeriesDataItem(new Millisecond(date), value));
	}

}
