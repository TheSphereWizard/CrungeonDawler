package ui;

import java.awt.Component;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class UILoader {
	static ArrayList<Component> ui= new ArrayList<Component>();
	public static ArrayList<Component> loadUI(String path,int w,int h){
		try{
			File xmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nNodes = doc.getChildNodes();
			for(int i=0;i<nNodes.getLength();i++){
				Node nNode = nNodes.item(i);
				if(nNode.getNodeType()==Node.ELEMENT_NODE){
					handleElement(nNode,null,w,h);
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return ui;
	}
	private static void handleElement(Node n,JPanel pComponent,int w,int h){
		String type = ((Element)n).getAttribute("type");
		String anchor = ((Element)n).getAttribute("anchor");
		String xOffset = ((Element)n).getAttribute("xOffset");
		String yOffset = ((Element)n).getAttribute("yOffset");
		String width = ((Element)n).getAttribute("width");
		String height = ((Element)n).getAttribute("height");
		Component nComponent = null;
		if(type.equals("panel")){
			nComponent = new JPanel();
			((JPanel)(nComponent)).setOpaque(false);
			((JPanel) nComponent).setLayout(null);
			NodeList nChildren = n.getChildNodes();
			for(int i=0;i<nChildren.getLength();i++){
				Node nChild = nChildren.item(i);
				if(nChild.getNodeType()==Node.ELEMENT_NODE){
					handleElement(nChild,(JPanel) nComponent,nComponent.getWidth(),nComponent.getHeight());
				}
			}
		}
		else if(type.equals("button")){
			nComponent = new JButton(((Element)n).getAttribute("text"));
		}
		else if(type.equals("label")){
			nComponent = new JLabel(((Element)n).getAttribute("text"));
		}
		//need to add actual class for minimap, and probably for images as well
		else if(type.equals("image")){
			nComponent = new JPanel();
		}
		else if(type.equals("meter")){
			nComponent = new JMeter(0,"");
		}
		else if(type.equals("miniMap")){
			nComponent = new JPanel();
		}
		int cX;
		int cY;
		int cW;
		int cH;
		int dX;
		int dY;
		if(anchor.equals("UL")){
			cX=0;
			cY=0;
			dX=1;
			dY=1;
		}
		else if(anchor.equals("UR")){
			cX=w;
			cY=0;
			dX=-1;
			dY=1;
		}
		else if(anchor.equals("BL")){
			cX=0;
			cY=h;
			dX=1;
			dY=-1;
		}
		else if(anchor.equals("BR")){
			cX=w;
			cY=h;
			dX=-1;
			dY=-1;
		}
		else{
			cX=0;
			cY=0;
			dX=1;
			dY=1;
		}
		if(xOffset.endsWith("px")){
			cX+=dX*Integer.valueOf(xOffset.substring(0, xOffset.length()-2));
		}
		else{
			cX+=dX*Integer.valueOf(xOffset)*w/100;
		}
		if(yOffset.endsWith("px")){
			cY+=dY*Integer.valueOf(yOffset.substring(0, yOffset.length()-2));
		}
		else{
			cY+=dY*Integer.valueOf(yOffset)*h/100;
		}
		if(width.endsWith("px")){
			cW=Integer.valueOf(width.substring(0, width.length()-2));
		}
		else{
			cW=Integer.valueOf(width)*w/100;
		}
		if(height.endsWith("px")){
			cH=Integer.valueOf(height.substring(0, height.length()-2));
		}
		else{
			cH=Integer.valueOf(height)*h/100;
		}
		nComponent.setBounds(cX,cY,cW,cH);
		if(pComponent == null){
			ui.add(nComponent);
		}
		else{
			pComponent.add(nComponent);
		}	
	}
	public static void main(String[] args){
		JFrame f = new JFrame();
		f.setBounds(0, 0, 200, 200);
		f.getContentPane().setLayout(null);
		UILoader u = new UILoader();
		ArrayList<Component> ui = u.loadUI("/git/CrungeonDawler/CrungeonDawler/resources/Interface/stupidUI.xml", 200,200);
		for(Component c:ui){
			f.getContentPane().add(c);
		}
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
