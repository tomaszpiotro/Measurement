package arduinoMeasurement.common;

import java.util.LinkedList;

public class FifoLimitedList<E> extends LinkedList<E>
{
	private static final long serialVersionUID = 5428945661836625695L;
	private int limit;
	
	public FifoLimitedList(final int limit)
	{
		super();
		this.limit = limit;
	}
	
	public void insert(final E element)
	{
		if(size() > limit)
		{
			removeLast();
		}
		addFirst(element);
	}
	
	public String toString(final String separator)
	{
		String result = "";
		for(E e: this)
		{
			result = result + separator + e.toString();
		}
		return result;
	}
	
	public void changeLimit(final int limit)
	{
		if(limit >=0 )
		{
			this.limit = limit;
		}
	}

}
