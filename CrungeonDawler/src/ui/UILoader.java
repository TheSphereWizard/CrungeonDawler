package ui;

import java.awt.Component;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class UILoader {
	ArrayList<Component> ui;
	public UILoader(){
		ui = new ArrayList<Component>();
	}
	public ArrayList<Component> loadUI(String path,int w,int h){
		try{
			File xmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList n = doc.getChildNodes();
			for(int i=0;i<n.getLength();i++){
				Node nNode = n.item(i);
				
			}
		}catch(Exception e){e.printStackTrace();}
		return ui;
	}
	private void handleElement(Node n){
		String type = ((Element)n).getAttribute("type");
		Component nComponent;
		if(type.equals("panel")){
			nComponent = new JPanel();
		}
		else if(type.equals("button")){
			nComponent = new JButton();
		}
		else if(type.equals("label")){
			nComponent = new JLabel();
		}
		//need to add actual classes for meter and minimap, and probably for images as well
		else if(type.equals("image")){
			nComponent = new JPanel();
		}
		else if(type.equals("meter")){
			nComponent = new JPanel();
		}
		else if(type.equals("miniMap")){
			nComponent = new JPanel();
		}

		NodeList nChildren = n.getChildNodes();
		for(int i=0;i<nChildren.getLength();i++){
			handleElement(nChildren.item(i));
		}
	}
}
