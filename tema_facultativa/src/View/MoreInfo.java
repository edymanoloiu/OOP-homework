/*
 * Created by JFormDesigner on Fri Dec 21 20:39:37 EET 2012
 */

package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import backend.CheckBoxNodeTree;
import backend.Serial;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Manoloiu Edmond
 */
public class MoreInfo extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imageLink;
	private BufferedImage picture;
	private Serial nou = new Serial();
	private JTree tree;
	private TreeMap<String,String> map = new TreeMap<>();

	public MoreInfo() throws FileNotFoundException, IOException,ClassNotFoundException {
		initComponents();
		setComponents();
		setImage();
		setTitle(nou.getNume());
	}

	public void setImage() throws IOException {
		URL url = new URL(imageLink);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream("image.jpg");
		byte[] b = new byte[2048];
		int length;
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		is.close();
		os.close();
		File file = new File("image.jpg");
		picture = ImageIO.read(file);
		Image image1 = picture;
		JLabel img = new JLabel(new ImageIcon(image1));
		image.add(img);
		file.delete();
	}

	public void setComponents() throws FileNotFoundException, IOException,
			ClassNotFoundException {
		long max = 0;
		File numeFis = null;
		File file = new File(".");
		File[] listFile = file.listFiles();
		for (File file2 : listFile) {
			if (!file2.isDirectory()) {
				if (file2.lastModified() > max) {
					numeFis = file2;
					max = file2.lastModified();
				}
			}
		}
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				numeFis));
		nou = (Serial) in.readObject();
		in.close();
		numeFis.delete();
		URL website = null;
		try {
			website = new URL(
					"http://services.tvrage.com/feeds/full_show_info.php?sid="
							+ nou.getShowID());
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
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(XmlFile);
			doc.getDocumentElement().normalize();
			Object[] ierarhie = null;
			int pozitie = 1;
			int sezon = 1;
			JList<Object> newList = new JList<Object>();
			NodeList nList = doc.getElementsByTagName("Show");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String linie = "";
					nume.setText(getTagValue("name", eElement));
					linie = linie + "Started: "
							+ getTagValue("started", eElement) + " ";
					if (!getTagValue("status", eElement).contains("Returning")
							|| getTagValue("status", eElement).contains(
									"Bubble"))
						linie = linie + "Ended: "
								+ getTagValue("ended", eElement);
					endStart.setText(linie);
					link.setText("Link site: "
							+ getTagValue("showlink", eElement));
					if (getTagValue("status", eElement).contains("Returning")) {
						oraDifuzare.setText("Ora difuzare: "
								+ getTagValue("airtime", eElement));
						ziDifuzare.setText("Zi difuzare: "
								+ getTagValue("airday", eElement));
						reteaDifuzare.setText("Reteaua de difuzare: "
								+ getTagValue("network", eElement));
					} else {
						oraDifuzare.setText("");
						ziDifuzare.setText("");
						reteaDifuzare.setText("");
					}
					imageLink = getTagValue("image", eElement);
					LinkedList<Object> list = new LinkedList<>();
					TreeMap<String, String> date = new TreeMap<String, String>();
					NodeList nList1 = doc.getElementsByTagName("episode");
					ierarhie = new Object[nList1.getLength()];
					ierarhie[0] = getTagValue("name", eElement);
					for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
						Node nNode1 = nList1.item(temp1);
						if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement1 = (Element) nNode1;
							if (temp1 < nList1.getLength() - 5)
								if (getTagValue("seasonnum", eElement1).equals("01") && temp1 != 0) {
									date.put(getTagValue("airdate", eElement1),
											getTagValue("title", eElement1));
									newList = new JList<>();
									newList.setListData(list.toArray());
									Object[] aux = list.toArray();
									Object[] ultim = new Object[aux.length + 1];
									ultim[0] = "Sezonul " + sezon;
									for (int i = 1; i < ultim.length; i++)
										ultim[i] = aux[i - 1];
									ierarhie[pozitie++] = ultim;
									sezon++;
									list = new LinkedList<>();
								}
							list.add(getTagValue("title", eElement1));
							date.put(getTagValue("airdate", eElement1),getTagValue("title", eElement1));
						}
					}
					Object[] aux = list.toArray();
					Object[] ultim = new Object[aux.length + 1];
					ultim[0] = "Sezonul " + sezon;
					for (int i = 1; i < ultim.length; i++)
						ultim[i] = aux[i - 1];
					ierarhie[pozitie++] = ultim;
					newList.setListData(aux);
				}
			}
			Object[] aux = new Object[sezon + 1];
			for (int i = 0; i <= sezon; i++) {
				aux[i] = ierarhie[i];
			}
			CheckBoxNodeTree pom = new CheckBoxNodeTree();
			tree = pom.getCheckBoxNodeTree(aux,nou.getEpisoade());
			episoade.add(tree);
			tree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					doMouseClicked(me);
				}
			});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	void doMouseClicked(MouseEvent me) {
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		if (tp != null) {
			int cuvant = 0;
			String numeEpisod = null,stare = null;
			StringTokenizer tok = new StringTokenizer(tp.toString(), "[]/,");
			while (tok.hasMoreTokens()) {
				String aux = tok.nextToken();
				if(cuvant == 4)
					numeEpisod = aux;
				if(cuvant == 5)
					stare = aux;
				cuvant ++;
			}
			map.put(numeEpisod, stare);
		}
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manoloiu Edmond
		nume = new JLabel();
		scrollPane2 = new JScrollPane();
		image = new JPanel();
		panel2 = new JPanel();
		endStart = new JLabel();
		link = new JLabel();
		oraDifuzare = new JLabel();
		ziDifuzare = new JLabel();
		reteaDifuzare = new JLabel();
		save = new JButton();
		back = new JButton();
		scrollPane1 = new JScrollPane();
		episoade = new JPanel();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"205dlu, $lcgap, 279dlu",
			"158dlu, $lgap, 177dlu"));

		//---- nume ----
		nume.setText("text");
		contentPane.add(nume, CC.xy(1, 1, CC.DEFAULT, CC.FILL));

		//======== scrollPane2 ========
		{

			//======== image ========
			{

				// JFormDesigner evaluation mark
				image.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), image.getBorder())); image.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

				image.setLayout(new GridLayout(1, 1));
			}
			scrollPane2.setViewportView(image);
		}
		contentPane.add(scrollPane2, CC.xy(3, 1, CC.DEFAULT, CC.FILL));

		//======== panel2 ========
		{
			panel2.setLayout(new FormLayout(
				"187dlu",
				"7*(default, $lgap), 35dlu, $lgap, 37dlu"));

			//---- endStart ----
			endStart.setText("text");
			panel2.add(endStart, CC.xy(1, 1));

			//---- link ----
			link.setText("text");
			panel2.add(link, CC.xy(1, 3));

			//---- oraDifuzare ----
			oraDifuzare.setText("text");
			panel2.add(oraDifuzare, CC.xy(1, 5));

			//---- ziDifuzare ----
			ziDifuzare.setText("text");
			panel2.add(ziDifuzare, CC.xy(1, 7));

			//---- reteaDifuzare ----
			reteaDifuzare.setText("text");
			panel2.add(reteaDifuzare, CC.xy(1, 9));

			//---- save ----
			save.setText("Salveaz\u0103 lista cu episoadele selectate");
			panel2.add(save, CC.xy(1, 15, CC.DEFAULT, CC.FILL));

			//---- back ----
			back.setText("\u00cenapoi la c\u0103utare");
			panel2.add(back, CC.xy(1, 17, CC.DEFAULT, CC.FILL));
		}
		contentPane.add(panel2, CC.xy(1, 3, CC.DEFAULT, CC.FILL));

		//======== scrollPane1 ========
		{

			//======== episoade ========
			{
				episoade.setLayout(new GridLayout(1, 1));
			}
			scrollPane1.setViewportView(episoade);
		}
		contentPane.add(scrollPane1, CC.xy(3, 3, CC.FILL, CC.FILL));
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
		back.addActionListener(this);
		save.addActionListener(this);
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manoloiu Edmond
	private JLabel nume;
	private JScrollPane scrollPane2;
	private JPanel image;
	private JPanel panel2;
	private JLabel endStart;
	private JLabel link;
	private JLabel oraDifuzare;
	private JLabel ziDifuzare;
	private JLabel reteaDifuzare;
	private JButton save;
	private JButton back;
	private JScrollPane scrollPane1;
	private JPanel episoade;
	// JFormDesigner - End of variables declaration //GEN-END:variables

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == back) {
			setVisible(false);
			try {
				UI fr = new UI();
				fr.setVisible(true);
				fr.setDefaultCloseOperation(EXIT_ON_CLOSE);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(arg0.getSource() == save)
		{
			TreeMap<String,String> aux = map;
			
			if(aux != null)
			{
				Collection<String> val = aux.values();
				Set<String> keys = aux.keySet();
				Object[] valori = val.toArray();
				Object[] chei = keys.toArray();
				for(int i=0; i<valori.length; i++)
				{
					nou.addEpisod((String)chei[i],(String)valori[i]);
				}
			}
			try {
				nou.save();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
