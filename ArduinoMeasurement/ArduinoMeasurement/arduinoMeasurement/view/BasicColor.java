package arduinoMeasurement.view;

import java.awt.Color;
import java.util.LinkedList;

public enum BasicColor
{
	Black(Color.BLACK),
	Blue(Color.BLUE),
	Cyan(Color.CYAN),
	Gray(Color.GRAY),
	Green(Color.GREEN),
	Magneta(Color.MAGENTA),
	Orange(Color.ORANGE),
	Pink(Color.PINK),
	Red(Color.RED),
	Yellow(Color.YELLOW),
	White(Color.WHITE);
	
	private Color color;
	
	BasicColor(final Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}

}
