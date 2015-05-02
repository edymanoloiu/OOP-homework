package backend;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeMap;

public class Serial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nume;
	private String showID;
	private String tara;
	private String status;
	private Image img;
	private String showlink;
	private String started;
	private String ended;
	private boolean end = false;
	private String nextEpisodeDate;
	private TreeMap <String,String> episoade;
	
	public Serial() {
		episoade = new TreeMap<String,String>();
	}
	public void setNextEpisode(String date) {
		nextEpisodeDate = date;
	}
	public boolean getEnd() {
		return end;
	}
	public void addEpisod(String nume, String status)
	{
		if(episoade == null)
			episoade = new TreeMap<String,String>();
		episoade.put(nume,status);
	}
	public String getNume() {
		return nume;
	}
	public void setEnd(boolean end) {
		this.end = end;
	}
	public void setShowID(String showID) {
		this.showID = showID;
	}
	public String getShowID() {
		return showID;
	}
	public void setTara(String tara) {
		this.tara = tara;
	}
	public String getTara() {
		return tara;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public Image getImg() {
		return img;
	}
	public void setShowlink(String showlink) {
		this.showlink = showlink;
	}
	public String getShowlink() {
		return showlink;
	}
	public void setStarted(String started) {
		this.started = started;
	}
	public String getStarted() {
		return started;
	}
	public void setEnded(String ended) {
		this.ended = ended;
	}
	public String getEnded() {
		return ended;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	@Override
	public String toString() {
		if(end)
			return nume + " Terminat";
		else
			return nume +" " + nextEpisodeDate;
	}
	public TreeMap<String, String> getEpisoade() {
		return episoade;
	}
	public void setEpisoade(TreeMap<String, String> episoade) {
		this.episoade = episoade;
	}
	public void save() throws FileNotFoundException, IOException
	{
		if(nume.indexOf('*') > -1)
			nume = nume.replace('*', '_');
		if( nume.indexOf('\\') > -1)
			nume = nume.replace('\\','_');
		if(nume.indexOf(':') > -1)
			nume = nume.replace(':', '_');
		if( nume.indexOf('/') > -1)
			nume = nume.replace('/', '_');
		if(nume.indexOf('?') > -1)
			nume = nume.replace('?', '_');
		if(nume.indexOf('\"') > -1)
			nume = nume.replace('\"' ,'_');
		if(nume.indexOf('<') > -1)
			nume = nume.replace('<' ,'_');
		if(nume.indexOf('>') > -1)
			nume = nume.replace('>','_');
		if(nume.indexOf('|') > -1)
			nume = nume.replace('|' ,'_');
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("Seriale" + File.separator + nume)));
		out.writeObject(this);
		out.close();
	}
	public void saveForMoreInfo() throws FileNotFoundException, IOException
	{
		if(nume.indexOf('*') > -1)
			nume = nume.replace('*', '_');
		if( nume.indexOf('\\') > -1)
			nume = nume.replace('\\','_');
		if(nume.indexOf(':') > -1)
			nume = nume.replace(':', '_');
		if( nume.indexOf('/') > -1)
			nume = nume.replace('/', '_');
		if(nume.indexOf('?') > -1)
			nume = nume.replace('?', '_');
		if(nume.indexOf('\"') > -1)
			nume = nume.replace('\"' ,'_');
		if(nume.indexOf('<') > -1)
			nume = nume.replace('<' ,'_');
		if(nume.indexOf('>') > -1)
			nume = nume.replace('>','_');
		if(nume.indexOf('|') > -1)
			nume = nume.replace('|' ,'_');
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(nume)));
		out.writeObject(this);
		out.close();
	}
}
