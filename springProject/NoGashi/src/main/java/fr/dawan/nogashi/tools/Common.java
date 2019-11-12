package fr.dawan.nogashi.tools;

public class Common 
{	
	public static boolean isEmpty(String value)
	{
		return ((value==null)||(value.isEmpty()));
	}
	
	public static Integer parseToInt(String value)
	{
		if(isEmpty(value))
			return null;
		
		Integer ret = null;
		try
		{
			ret = Integer.parseInt(value);
		}catch(NumberFormatException e) {
			return null;
		}
		return ret;
	}
}
