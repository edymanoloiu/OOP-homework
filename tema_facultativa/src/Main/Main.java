package Main;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import View.UI;

public class Main {

	public static void main(String[] args) throws MalformedURLException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
		UI frame = new UI();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
