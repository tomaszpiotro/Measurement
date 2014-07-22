package arduinoMeasurement.view;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import arduinoMeasurement.common.FifoLimitedList;


public class LogWindow extends JFrame
{
	private final static int MAX_MESSAGES_COUNT = 200;
	private final static String windowName = "Log";
	private String log;
	private final static int X_DIMENSION = 400;
	private final static int Y_DIMENSION = 300;
	private final JEditorPane textPanel;
	private final JScrollPane scrollPane;
	private final FifoLimitedList<String> messages;

	
	public LogWindow()
	{
		super();
		setTitle(windowName);
		setSize(X_DIMENSION, Y_DIMENSION);
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		log = "";
		this.textPanel = new JEditorPane("text/html", log);

		scrollPane = new JScrollPane(textPanel);
		add(scrollPane);
		textPanel.setEditable(false);
		this.messages = new FifoLimitedList<String>(MAX_MESSAGES_COUNT);

	}
	
	void addNewEntrance(final String message)
	{
		final int position = scrollPane.getVerticalScrollBar().getValue();
		messages.insert("<b>" + DateFormat.getTimeInstance().format(new Date()) + "</b> "+ message);
		
		
		String insertedHtml = "<br><b>" + DateFormat.getTimeInstance().format(new Date()) + "</b> "+ message;
		log = insertedHtml + log;
		textPanel.setText(messages.toString("<br>"));
		/*		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				scrollPane.getVerticalScrollBar().setValue(position + LOG_LINE_WIDTH);	
			}
		});
		*/
	}
	
	void setVisible()
	{
		setVisible(true);
	}
}
