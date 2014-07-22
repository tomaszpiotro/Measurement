package arduinoMeasurement.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.Millisecond;
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
	private String[] lineRenderOptions = {"XY Spline", "Blank", "Points only", "Sharp", "Sharp with points"};
	private JComboBox<String> timeSeriesChooser;
	private JComboBox<BasicColor> colorChooser;
	private final JButton minRangeChangeButton;
	private JTextField minRange;
	private final JButton maxRangeChangeButton;
	private JTextField maxRange;
	
	
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
		addLineRenderOptionListener();
		controlPanel.add(new JLabel("Set chart style"));
		controlPanel.add(lineRenderOptionsChooser);
		
		controlPanel.add(new JLabel("Choose series:"));
		timeSeriesChooser = makeSeriesNamesComboBox();
		controlPanel.add(timeSeriesChooser);
		colorChooser = new JComboBox<BasicColor>(BasicColor.values());
		controlPanel.add(new JLabel("Choose color:"));
		controlPanel.add(colorChooser);
		addColorOptionListener();
		minRangeChangeButton = new JButton("Set minimal range:");
		controlPanel.add(minRangeChangeButton);
		minRange = new JTextField();
		controlPanel.add(minRange);
		addMinRangeListener();
		maxRangeChangeButton = new JButton("Set maximal range:");
		controlPanel.add(maxRangeChangeButton);
		maxRange = new JTextField();
		controlPanel.add(maxRange);
		addMaxRangeListener();
		addChartListener();
		
	}
	
	private void addChartListener()
	{
		XYPlot xyPlot = (XYPlot) chartPanel.getChart().getPlot();
		xyPlot.getRangeAxis().addChangeListener(new AxisChangeListener()
		{
			@Override
			public void axisChanged(AxisChangeEvent arg0)
			{
				changeAxisTextFields();
			}
		});
	}
	
	private void changeAxisTextFields()
	{
		XYPlot xyPlot = (XYPlot) chartPanel.getChart().getPlot();
		Range range = xyPlot.getRangeAxis().getRange();
		double minRange = range.getLowerBound();
		double maxRange = range.getUpperBound();
		this.minRange.setText(String.valueOf(minRange));
		this.maxRange.setText(String.valueOf(maxRange));
	}

	private void addMaxRangeListener()
	{
		maxRangeChangeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String maxRangeText = maxRange.getText();
				try
				{
					float maxRange = Float.parseFloat(maxRangeText);
					XYPlot xyPlot = (XYPlot) chartPanel.getChart().getPlot();
					double minRange = xyPlot.getRangeAxis().getRange().getLowerBound();
					if(minRange < maxRange)
					{
						xyPlot.getRangeAxis().setRange(new Range(minRange, maxRange));
					}
				}
				catch(NumberFormatException e)
				{
					System.out.println(e);
				}
			}
			
		});
		
	}

	private void addMinRangeListener()
	{
		minRangeChangeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String minRangeText = minRange.getText();
				try
				{
					float minRange = Float.parseFloat(minRangeText);
					XYPlot xyPlot = (XYPlot) chartPanel.getChart().getPlot();
					double maxRange = xyPlot.getRangeAxis().getRange().getUpperBound();
					if(minRange < maxRange)
					{
						xyPlot.getRangeAxis().setRange(new Range(minRange, maxRange));
					}
				}
				catch(NumberFormatException e)
				{
					
				}
			}
		});
		
	}

	private void addColorOptionListener()
	{
		colorChooser.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				changeSelectedSeriesColor();
			}
		});
	}
	
	private void addLineRenderOptionListener()
	{
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
						/*basicChart.getXYPlot().getRenderer().setSeriesStroke(0, 
							    new BasicStroke(
							            2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,
							            1.0f, new float[] {6.0f, 6.0f}, 0.0f));
							            */
						break;
					case "Blank":
						basicChart.getXYPlot().setRenderer(new StandardXYItemRenderer(0));
						break;
					case "Points only":
						basicChart.getXYPlot().setRenderer(new StandardXYItemRenderer(1));
						break;
					case "Sharp":
						basicChart.getXYPlot().setRenderer(new StandardXYItemRenderer(2));
						break;
					case "Sharp with points":
						basicChart.getXYPlot().setRenderer(new StandardXYItemRenderer(3));
						break;
				}
				changeSelectedSeriesColor();
				
			}
		});
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
		chart.getXYPlot().setRenderer(new StandardXYItemRenderer(1));
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
	
	void changeControlPanelVisibility()
	{
		controlPanel.setVisible(!controlPanel.isVisible());
	}

	private void changeSelectedSeriesColor()
	{
		BasicColor selectedOption = (BasicColor)colorChooser.getSelectedItem();
		basicChart.getXYPlot().getRenderer().setSeriesPaint(getSelectedSeriesIndex(), selectedOption.getColor());
	}

}
