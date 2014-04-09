package arduinoMeasurement.common;

import java.util.Collections;
import java.util.LinkedList;

public class SortedList<E extends Comparable<E>> extends LinkedList<E>
{
	private static final long serialVersionUID = 6248954453604293809L;

	public SortedList()
	{
		super();
	}
	
	@Override
	public boolean add(E e)
	{
		boolean result = super.add(e);
		Collections.sort(this);
		return result;
	}

}
