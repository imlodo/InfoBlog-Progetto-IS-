package model.bean;

import java.util.ArrayList;

public class Conversazione 
{
	private ArrayList<Messagio> messaggi;
	private String convCon;
	
	public Conversazione()
	{
		messaggi=new ArrayList<Messagio>();
		convCon="";
	}
	public String getConvCon() {
		return convCon;
	}

	public void setConvCon(String convCon) {
		this.convCon = convCon;
	}

	public ArrayList<Messagio> getMessaggi() {
		return messaggi;
	}

	public void setMessaggi(Messagio messaggi) 
	{
		this.messaggi.add(messaggi);
	}
	@Override
	public String toString() {
		return "Conversazione [messaggi=" + messaggi + ", convCon=" + convCon + "]";
	}
	
	/*public static Conversazione createConversazione(ArrayList<Messagio> mex,int inizio,int fine)
	{
		Conversazione conv=new Conversazione();
		conv.setConvCon(mex.get(inizio).getMittente());
		
		for(;inizio<fine;inizio++)
			conv.setMessaggi(mex.get(inizio));
		return conv;
	}*/
}
