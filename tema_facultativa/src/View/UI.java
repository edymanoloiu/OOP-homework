/*
 * Created by JFormDesigner on Fri Dec 21 17:19:27 EET 2012
 */

package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.swing.*;

import backend.Serial;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Manoloiu Edmond
 */
public class UI extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Serial serial = new Serial();
	private TreeMap<String, Serial> map = new TreeMap<>();
	private Object[] listFav;
	private Serial nou;

	public UI() throws FileNotFoundException, ClassNotFoundException,IOException, ParserConfigurationException, SAXException {
		initComponents();
		setTitle("My TV Series Scheduler");
		File file = new File("Seriale");
		if (!file.exists())
			file.mkdir();
		getFavorite();
	}

	public void getFavorite() throws FileNotFoundException, IOException,ClassNotFoundException {
		File file = new File("Seriale");
		String[] list = file.list();
		listFav = new Object[list.length];
		if (list.length != 0) {
			for (int i = 0; i < list.length; i++) {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("Seriale" + File.separator + list[i])));
					Serial nou = (Serial) in.readObject();
					listFav[i] = nou.toString();
					in.close();
			}
		}
		list_fav.setListData(listFav);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manoloiu Edmond
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		title = new JTextField();
		label2 = new JLabel();
		search = new JButton();
		scrollPane2 = new JScrollPane();
		list_fav = new JList<Object>();
		scrollPane1 = new JScrollPane();
		list_view = new JList<Object>();
		delete = new JButton();
		panel1 = new JPanel();
		add = new JButton();
		moreinfo = new JButton();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);

			// JFormDesigner evaluation mark
			dialogPane
					.setBorder(new javax.swing.border.CompoundBorder(
							new javax.swing.border.TitledBorder(
									new javax.swing.border.EmptyBorder(0, 0, 0,
											0), "JFormDesigner Evaluation",
									javax.swing.border.TitledBorder.CENTER,
									javax.swing.border.TitledBorder.BOTTOM,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									java.awt.Color.red), dialogPane.getBorder()));
			dialogPane
					.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							if ("border".equals(e.getPropertyName()))
								throw new RuntimeException();
						}
					});

			dialogPane.setLayout(new BorderLayout());

			// ======== contentPanel ========
			{
				contentPanel.setLayout(new FormLayout("175dlu, 249dlu",
						"26dlu, $lgap, default, $lgap, 134dlu, $lgap, 29dlu"));

				// ---- label1 ----
				label1.setText("Introduce\u021bii titlul serialului c\u0103utat");
				contentPanel.add(label1, CC.xy(1, 1, CC.FILL, CC.FILL));
				contentPanel.add(title, CC.xy(2, 1, CC.DEFAULT, CC.FILL));

				// ---- label2 ----
				label2.setText("Lista serialelor urm\u0103rite este:");
				contentPanel.add(label2, CC.xy(1, 3, CC.DEFAULT, CC.FILL));

				// ---- search ----
				search.setText("C\u0103utare");
				contentPanel.add(search, CC.xy(2, 3));

				// ======== scrollPane2 ========
				{
					scrollPane2.setViewportView(list_fav);
				}
				contentPanel.add(scrollPane2, CC.xy(1, 5, CC.DEFAULT, CC.FILL));

				// ======== scrollPane1 ========
				{
					scrollPane1.setViewportView(list_view);
				}
				contentPanel.add(scrollPane1, CC.xy(2, 5, CC.DEFAULT, CC.FILL));

				// ---- delete ----
				delete.setText("Elimin\u0103 serialul urm\u0103rit selectat");
				contentPanel.add(delete, CC.xy(1, 7, CC.FILL, CC.FILL));

				// ======== panel1 ========
				{
					panel1.setLayout(new FormLayout("168dlu, $lcgap, 75dlu",
							"29dlu"));

					// ---- add ----
					add.setText("Adaug\u0103 serialul selectat la seriale urm\u0103rite");
					panel1.add(add, CC.xy(1, 1, CC.FILL, CC.FILL));

					// ---- moreinfo ----
					moreinfo.setText("More Info");
					panel1.add(moreinfo, CC.xy(3, 1, CC.FILL, CC.FILL));
				}
				contentPanel.add(panel1, CC.xy(2, 7));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
		search.addActionListener(this);
		add.addActionListener(this);
		moreinfo.addActionListener(this);
		delete.addActionListener(this);
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manoloiu Edmond
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JTextField title;
	private JLabel label2;
	private JButton search;
	private JScrollPane scrollPane2;
	private JList<Object> list_fav;
	private JScrollPane scrollPane1;
	private JList<Object> list_view;
	private JButton delete;
	private JPanel panel1;
	private JButton add;
	private JButton moreinfo;

	// JFormDesigner - End of variables declaration //GEN-END:variables
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	public void setSerial() throws ParserConfigurationException, SAXException,IOException {
		URL website = new URL("http://services.tvrage.com/feeds/full_show_info.php?sid=" + nou.getShowID());
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("information.xml");
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		fos.close();
		rbc.close();
		File XmlFile = new File("information.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Show");
		TreeMap<String, String> date = new TreeMap<String, String>();
		NodeList nList1 = doc.getElementsByTagName("episode");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
					Node nNode1 = nList1.item(temp1);
					if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement1 = (Element) nNode1;
						if (temp1 < nList1.getLength() - 5)
							if (getTagValue("seasonnum", eElement1).equals("01") && temp1 != 0){
								date.put(getTagValue("airdate", eElement1),getTagValue("title", eElement1));
							}
						date.put(getTagValue("airdate", eElement1),getTagValue("title", eElement1));
					}
				}
				if (!nou.getEnd()) {
					Entry<String, String> data = date.lastEntry();
					nou.setNextEpisode(data.getKey());
				}
			}
		}
		XmlFile.delete();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == search) {
			String name = title.getText();
			if(name.indexOf(' ') > -1)
				name = name.replace(" ", "%20");
			URL website = null;
			try {
				website = new URL("http://services.tvrage.com/feeds/search.php?show=" + name);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ReadableByteChannel rbc = null;
			try {
				rbc = Channels.newChannel(website.openStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream("information.xml");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				fos.getChannel().transferFrom(rbc, 0, 1 << 24);
				fos.close();
				rbc.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				File XmlFile = new File("information.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(XmlFile);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("show");
				LinkedList<String> list = new LinkedList<>();
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						serial = new Serial();
						String linie = "";
						serial.setShowID(getTagValue("showid", eElement));
						serial.setNume(getTagValue("name", eElement));
						serial.setTara(getTagValue("country", eElement));
						serial.setStatus(getTagValue("status", eElement));
						linie = linie + getTagValue("showid", eElement) + " ";
						linie = linie + getTagValue("name", eElement) + " ";
						linie = linie + getTagValue("country", eElement) + " ";
						linie = linie + getTagValue("status", eElement) + " ";
						if (getTagValue("status", eElement).contains("Ended"))
							serial.setEnd(true);
						list.add(linie);
						map.put(serial.getShowID(), serial);
					}
				}
				Object[] nou = list.toArray();
				list_view.setListData(nou);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == add) {
			StringTokenizer tok = new StringTokenizer((String) list_view.getSelectedValue());
			nou = map.get(tok.nextToken());
			try {
				setSerial();
			} catch (ParserConfigurationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SAXException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				nou.save();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				getFavorite();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource() == moreinfo) {
			if (list_view.getSelectedValue() != null) {
				StringTokenizer tok = new StringTokenizer((String) list_view.getSelectedValue());
				nou = map.get(tok.nextToken());
			} else {
				String numeSerial = (String) list_fav.getSelectedValue();
				File file = new File("Seriale");
				File list[] = file.listFiles();
				for (int i = 0; i < list.length; i++) {
					if (numeSerial.contains(list[i].getName())) {
						ObjectInputStream in;
						try {
							in = new ObjectInputStream(new FileInputStream(
									list[i]));
							nou = (Serial) in.readObject();
							in.close();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					}
				}
			}
			try {
				nou.saveForMoreInfo();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setVisible(false);
			MoreInfo info;
			try {
				info = new MoreInfo();
				info.setVisible(true);
				info.setDefaultCloseOperation(EXIT_ON_CLOSE);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		if (e.getSource() == delete) {
			String numeSerial = (String) list_fav.getSelectedValue();
			System.gc();
			File file = new File("Seriale");
			File list[] = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (numeSerial.contains(list[i].getName())) {
					list[i].setWritable(true);
					list[i].delete();
					i = list.length;
				}
			}
			try {
				getFavorite();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
