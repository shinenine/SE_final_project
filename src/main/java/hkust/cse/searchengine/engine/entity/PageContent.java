package hkust.cse.searchengine.engine.entity;

import jdbm.RecordManager;
import jdbm.htree.HTree;

import java.io.IOException;

public class PageContent
{
	private RecordManager recman;
	private HTree hashtable;

	public PageContent(RecordManager recordmanager, String objectname) throws IOException
	{
		this.recman = recordmanager;
		long recid = recman.getNamedObject(objectname);

		if(recid != 0)
			hashtable = HTree.load(recman, recid);
		else
		{
			hashtable = HTree.createInstance(recman);
			recman.setNamedObject(objectname,hashtable.getRecid());
		}
	}
	
	 public void finalize() throws IOException
   	 {
       		 recman.commit();
       		 recman.close();                
   	 } 

	public void addEntry(int pageID, Page page) throws IOException
	{
		Page content = (Page)hashtable.get(pageID);
		if(content == null){
			hashtable.put(pageID, page);
		}else{
			if(page.getModifiedDate() == null||page.getModifiedDate().after(content.getModifiedDate())){
				content = page;
				hashtable.put(pageID, content);
			}
		}
	}

	public void delEntry(int pageID) throws IOException
	{
		hashtable.remove(pageID);	
	}

	public Page getPageContent(int pageID) throws IOException
	{
		Page content = (Page)hashtable.get(pageID);
		if(content == null)
			return null;
		else
			return content;
	}
	
}
//
