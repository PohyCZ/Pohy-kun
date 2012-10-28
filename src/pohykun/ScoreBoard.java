package pohykun;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ScoreBoard {
	private String filePath;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	Document doc;
	
	private static String won = "won";
	private static String played = "played";
	private static String time = "time";
	
	public ScoreBoard(String filePath) {
		this.filePath = filePath;
		
		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(filePath);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(String _player, String _time) {
		Element docElement = doc.getDocumentElement();
		
		Element playerNode = doc.createElement(_player);
		docElement.appendChild(playerNode);
		
		Element wonNode = doc.createElement(won);
		wonNode.setTextContent("0");
		playerNode.appendChild(wonNode);
		docElement.appendChild(playerNode);
		
		Element playedNode = doc.createElement(played);
		playedNode.setTextContent("1");
		playerNode.appendChild(playedNode);
		docElement.appendChild(playerNode);
		
		Element lastPlayedNode = doc.createElement(time);
		lastPlayedNode.setTextContent(_time);
		playerNode.appendChild(lastPlayedNode);
		docElement.appendChild(playerNode);
		
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String player, String element, String value) {
		Node players = doc.getFirstChild();
		NodeList playerNode = null;
		
		NodeList list = players.getChildNodes();
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			
			if(player.equals(node.getNodeName()))
				playerNode = node.getChildNodes();
		}
		
		for(int i = 0; i < playerNode.getLength(); i++) {
			Node node = playerNode.item(i);
			
			if(element.equals(node.getNodeName()))
				node.setTextContent(value);
		}
		
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public String read(String player, String element) {
		String value = null;
		Node players = doc.getFirstChild();
		NodeList playerNode = null;
		
		NodeList list = players.getChildNodes();
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			
			if(player.equals(node.getNodeName()))
				playerNode = node.getChildNodes();
//				System.out.println(node.getFirstChild());
		}
		
		if(playerNode != null) {
			for(int i = 0; i < playerNode.getLength(); i++) {
				Node node = playerNode.item(i);
				
				if(element.equals(node.getNodeName()))
					value = node.getTextContent();
			}
		}
		
		return value;
	}

}
