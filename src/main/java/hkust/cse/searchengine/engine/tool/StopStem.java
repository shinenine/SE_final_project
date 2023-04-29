package hkust.cse.searchengine.engine.tool;

import hkust.cse.searchengine.engine.tool.Porter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

public class StopStem
{
	private Porter porter;
	private java.util.HashSet stopWords;
	public boolean isStopWord(String str)
	{
		return stopWords.contains(str);	
	}
	public StopStem(String str)
	{
		super();
		porter = new Porter();
		stopWords = new java.util.HashSet();
				
		try{
			Resource resource = new ClassPathResource(str);
			InputStream fin = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			String r = "";
			while((r = br.readLine())!= null)
				stopWords.add(r);
		}
		catch(IOException ioe){
			System.err.println(ioe.toString());
		} 
		
	}
	public String stem(String str)
	{
		return porter.stripAffixes(str);
	}
/*	public static void main(String[] arg)
	{
		StopStem stopStem = new StopStem("stopwords.txt");
		String input="";
		try{
			do
			{
				System.out.print("Please enter a single English word: ");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				input = in.readLine();
				if(input.length()>0)
				{	
					if (stopStem.isStopWord(input))
						System.out.println("It should be stopped");
					else
			   			System.out.println("The stem of it is \"" + stopStem.stem(input)+"\"");
				}
			}
			while(input.length()>0);
		}
		catch(IOException ioe)
		{
			System.err.println(ioe.toString());
		}
	}*/
}
//
