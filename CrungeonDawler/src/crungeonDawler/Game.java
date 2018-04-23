package crungeonDawler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Game {
	Level currentLevel;
	Player player;
	public ArrayList<Entity> allEntities =new ArrayList<Entity>();
	final int TILE_SIZE = 16;
	private Image tileSet;
	private Image decorationSet;

	public void DrawMenu(Graphics2D g2d, Point mousePosition) {
		Draw(g2d,mousePosition);
		g2d.translate(-Screen.frameWidth/2,-Screen.frameHeight/2);//in future don't translate
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(new Color(75,75,75,255));
		g2d.fillRect(Screen.frameWidth/3,Screen.frameHeight/3,Screen.frameWidth/3,Screen.frameHeight/3);
	}
	public static final int pixelTileWidth =16;
	public static final int renderdist = 25;
	public void Draw(Graphics2D g2d, Point mousePosition) {
		//Hey Just want to note that the black lines flashing onto the screen
		//only appear moving left or up, never down or right
		//because as we new it begins redrawing before being finished before
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		for(int x=Math.max(player.getX()/pixelTileWidth-renderdist,0);x<currentLevel.width&&x<player.getX()/pixelTileWidth+renderdist+1;x++){
			for(int y=Math.max(player.getY()/pixelTileWidth-renderdist,0);y<currentLevel.height&&y<player.getY()/pixelTileWidth+renderdist+1;y++){
				dungeon.getGraphics().drawImage(getImageFromTileID(currentLevel.levellayout[x][y]),x*pixelTileWidth-player.getX()-8+dungeon.getWidth()/2,y*pixelTileWidth-player.getY()-8+dungeon.getHeight()/2, pixelTileWidth, pixelTileWidth,null);
			}
		}
		
		for(Entity e : allEntities){
			if(Math.abs(e.getX()-player.getX())<renderdist&& Math.abs(e.getY()-player.getY())<renderdist){
				dungeon.getGraphics().drawImage(e.getSprite(), (e.getX()-player.getX()-8)+dungeon.getWidth()/2, (e.getY()-player.getY()-8)+dungeon.getHeight()/2,e.getWidth(),e.getHeight(), null);
			}
		}
//		BufferedImage vis = getVisible();
//
//		for(int x=0;x<dungeon.getWidth();x++){
//			for(int y=0;y<dungeon.getHeight();y++){
//				if(vis.getRGB(x, y)!=-1){
//					dungeon.setRGB(x, y, -16777216);
//				}
//			}
//		}
//		dungeon.getGraphics().drawImage(vis, 0, 0, null);
		g2d.drawImage(dungeon, (Screen.frameWidth-dungeon.getWidth())/2,(Screen.frameHeight-dungeon.getHeight())/2,null);
	}
	public BufferedImage getVisible3(){
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		Graphics2D g = (Graphics2D)dungeon.getGraphics();
		ArrayList<int[]> poly = new ArrayList<int[]>();
		int[] realcenter = new int[]{player.x,player.y};
		int[] roundedcenter = new int[]{(int)((player.x+8)/pixelTileWidth)*pixelTileWidth+8,(int)((player.y+8)/pixelTileWidth)*pixelTileWidth+8};
		g.translate(-player.x+dungeon.getWidth()/2, -player.y+dungeon.getHeight()/2);
		
		g.drawRect(roundedcenter[0]-16,roundedcenter[1]-16, 16, 16);
		
		for(int x=-player.lengthOfLineOfSight;x<=player.lengthOfLineOfSight;x++){
			for(int y=-player.lengthOfLineOfSight;y<=player.lengthOfLineOfSight;y++){
				if(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))<player.lengthOfLineOfSight){
					
				}
			}
		}
		
		poly=MergeSortbyangle(poly);
		Polygon polygon = new Polygon();
		for(int[] r : poly){
			polygon.addPoint(r[0], r[1]);
		}
		g.translate(realcenter[0], realcenter[1]);
		g.fillPolygon(polygon);
		return dungeon;
	}
	public BufferedImage getVisible2(){
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		Graphics2D g = (Graphics2D)dungeon.getGraphics();
		ArrayList<int[]> poly = new ArrayList<int[]>();
		int[] realcenter = new int[]{player.x,player.y};
		int[] roundedcenter = new int[]{(int)((player.x+8)/pixelTileWidth)*pixelTileWidth+8,(int)((player.y+8)/pixelTileWidth)*pixelTileWidth+8};
		g.translate(-player.x+dungeon.getWidth()/2, -player.y+dungeon.getHeight()/2);
		
		for(double theta=0;theta<Math.PI*2;theta+=Math.PI*2/107){
			double xrange = Math.cos(theta)*player.lengthOfLineOfSight*pixelTileWidth;
			double yrange = Math.sin(theta)*player.lengthOfLineOfSight*pixelTileWidth;
			double m = Math.tan(theta);
			
			g.setColor(Color.white);
			
//			for(int i=-10;i<=10;i++){
//				g.drawLine(roundedcenter[0]-10*pixelTileWidth, roundedcenter[1]+i*pixelTileWidth, roundedcenter[0]+10*pixelTileWidth, roundedcenter[1]+i*pixelTileWidth);
//				g.drawLine(roundedcenter[0]+i*pixelTileWidth, roundedcenter[1]-10*pixelTileWidth, roundedcenter[0]+i*pixelTileWidth, roundedcenter[1]+10*pixelTileWidth);
//			}
			
			
			ArrayList<double[]> yintersections = new ArrayList<double[]>();
			for(double i=0;Math.abs(i)<Math.abs(xrange);i+=Math.signum(xrange)*pixelTileWidth){
				yintersections.add(new double[]{i-realcenter[0]%16+8, m*(i-realcenter[0]%16+8)});
			}
			for(int i=0;i<yintersections.size();i++){
				if(Math.sqrt(Math.pow(yintersections.get(i)[0],2)+Math.pow(yintersections.get(i)[1], 2))>Math.sqrt(Math.pow(xrange,2)+Math.pow(yrange, 2))){
					yintersections.remove(i);
					i--;
				}
			}
			yintersections=MergeSortbydistance(yintersections);
			ArrayList<int[]> yroundings = new ArrayList<int[]>();
			for(int i=0;i<yintersections.size();i++){
				int[] y= new int[]{Read.roundto(yintersections.get(i)[0],16),Read.roundto(yintersections.get(i)[1]+8,16)};
				if(yintersections.get(i)[0]<0){
					y[0]-=32;
				}
				if(yintersections.get(i)[1]<0){
					y[1]-=32;
				}else{
					y[1]-=16;
				}
				yroundings.add(y);

			}
			ArrayList<double[]> xintersections = new ArrayList<double[]>();
			for(int i=0;Math.abs(i)<Math.abs(yrange);i+=Math.signum(yrange)*pixelTileWidth){
				xintersections.add(new double[]{(i-realcenter[1]%16+8)/m,i-realcenter[1]%16+8});
			}
			for(int i=0;i<xintersections.size();i++){
				if(Math.sqrt(Math.pow(xintersections.get(i)[0],2)+Math.pow(xintersections.get(i)[1], 2))>Math.sqrt(Math.pow(xrange,2)+Math.pow(yrange, 2))){
					xintersections.remove(i);
					i--;
				}
			}
			xintersections=MergeSortbydistance(xintersections);
			ArrayList<int[]> xroundings = new ArrayList<int[]>();
			for(int i=0;i<xintersections.size();i++){
				int[] y= new int[]{Read.roundto(xintersections.get(i)[0]+8,16),Read.roundto(xintersections.get(i)[1],16)};
				y[0]-=32;
				if(xintersections.get(i)[1]<0){
					y[1]-=32;
				}
				if(roundedcenter[1]==realcenter[1]+16&xintersections.get(i)[0]<0){
					y[1]-=16;
				}
				xroundings.add(y);
			}
			
			
			int i=0;
			int x=0;
			int y=0;
			int whathappened=0;//0=no wall, 1 =xwall,2=ywall
			out:
			for(;i<xintersections.size() + yintersections.size();i++){
				if(i%2==0){
					try{
						if(Read.contains(player.invalidtiles,currentLevel.levellayout[(int) ((xintersections.get(i)[0]+Read.roundto(player.x, 16))/pixelTileWidth)][(int) ((xintersections.get(i)[1]+Read.roundto(player.y, 16))/pixelTileWidth)])){
							whathappened=1;
							break out;
						}
					}catch(IndexOutOfBoundsException e){}
					x++;
				}else{
					try{
						if(Read.contains(player.invalidtiles,currentLevel.levellayout[(int) ((yintersections.get(i)[0]+Read.roundto(player.x, 16))/pixelTileWidth)][(int) ((yintersections.get(i)[1]+Read.roundto(player.y, 16))/pixelTileWidth)])){
							whathappened=2;
							break out;
						}
					}catch(IndexOutOfBoundsException e){}
					y++;
				}
			}
			if(whathappened==0){
//				g.setColor(new Color(255,255,255));
//				g.drawLine(cen[0],cen[1],cen[0]+(int)(p.get(i)[0]),cen[1]+(int)(p.get(i)[1]));
//				poly.add(new int[]{(int)(p.get(i)[0]),(int)(p.get(i)[1])});
//				poly.add(new int[]{a.get(i)[0]-8,a.get(i)[1]-8});
//				g.setColor(new Color(255,255,255,128));
//				g.drawLine(realcenter[0], realcenter[1], (int)(realcenter[0]+xrange),(int)(realcenter[1]+yrange));
				poly.add(new int[]{(int)(xrange-16),(int)(yrange-16)});
			}else{
				if(whathappened==1){
					g.drawOval((int)xintersections.get(x)[0], (int)xintersections.get(x)[1], 2, 2);
					poly.add(new int[]{(int)xintersections.get(x)[0], (int)xintersections.get(x)[1]});
				}else{
					g.drawOval((int)yintersections.get(y)[0], (int)yintersections.get(y)[1], 2, 2);
					poly.add(new int[]{(int)yintersections.get(y)[0], (int)yintersections.get(y)[1]});
				}
			}
		}
		
		g.drawOval((int)(realcenter[0]-player.lengthOfLineOfSight*pixelTileWidth), (int)(realcenter[1]-player.lengthOfLineOfSight*pixelTileWidth),(int)player.lengthOfLineOfSight*pixelTileWidth*2, (int)player.lengthOfLineOfSight*pixelTileWidth*2);
		poly=MergeSortbyangle(poly);
		Polygon polygon = new Polygon();
		for(int[] r : poly){
			polygon.addPoint(r[0], r[1]);
		}
		g.translate(realcenter[0], realcenter[1]);
		g.fillPolygon(polygon);
		return dungeon;
	}
	public BufferedImage getVisible() {
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		Graphics2D g = (Graphics2D)dungeon.getGraphics();
		ArrayList<int[]> poly = new ArrayList<int[]>();
		g.translate(-player.x+dungeon.getWidth()/2, -player.y+dungeon.getHeight()/2);
		int[] cen = new int[]{player.x+8,player.y+8};
		int[] testcenter = new int[]{(int)((player.x)/pixelTileWidth)*pixelTileWidth,(int)((player.y)/pixelTileWidth)*pixelTileWidth};
		for(double theta=0;theta<Math.PI*2;theta+=Math.PI*2/100){
			double xrange = Math.cos(theta)*player.lengthOfLineOfSight*pixelTileWidth;
			double yrange = Math.sin(theta)*player.lengthOfLineOfSight*pixelTileWidth;
			double m = Math.tan(theta);
			ArrayList<double[]> p = new ArrayList<double[]>();
			

			
			for(double i=0;Math.abs(i)<Math.abs(xrange*2);i+=Math.signum(xrange)*pixelTileWidth){
				p.add(new double[]{i-cen[0]%16+8, m*(i-cen[0]%16+8)});
			}
			for(int i=0;Math.abs(i)<Math.abs(yrange*2);i+=Math.signum(yrange)*pixelTileWidth){
				p.add(new double[]{(i-cen[1]%16+8)/m,i-cen[1]%16+8});
			}
			p=MergeSortbydistance(p);
			
			///////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////
			//THIS CODE TRIES TO FIGURE OUT WHAT BOXES NEED TO BE CHECKED FOR EACH GRID LINE INTERSECTION
			//I HAVE NO IDEA HOW THE MATH SHOULD ACTUALLY BE DONE AND THIS IS ONE OF THE LARGE SOURCES OF TERRIBLENESS
			ArrayList<int[]> a = new ArrayList<int[]>();
			for(int i=0;i<p.size();i++){
				if(p.get(i)[0]-(int)p.get(i)[0]==0d){
//					if(!arraycontains(a,new int[]{Read.roundto(p.get(i)[0]+8,16),Read.roundto(p.get(i)[1]+8,16)}))
						a.add(new int[]{Read.roundto(p.get(i)[0],16),Read.roundto(p.get(i)[1],16)});
				}else{
//					if(!arraycontains(a,new int[]{Read.roundto(p.get(i)[0]+8,16),Read.roundto(p.get(i)[1]+8,16)}))
						a.add(new int[]{Read.roundto(p.get(i)[0],16),Read.roundto(p.get(i)[1],16)});
				}
			}
			boolean hitwall=false;
			int i=0;
			out:
			//Fine maybe instead of rounding
				//can rewrite entirely with a list of grid boxes within some dist of player
				//then draw line if doesn't intersect any of boxes
				
				
			for(;i<a.size();i++){
				try{
					if(Read.contains(player.invalidtiles,currentLevel.levellayout[(a.get(i)[0]+Read.roundto(player.x, 16))/pixelTileWidth][(a.get(i)[1]+Read.roundto(player.y, 16))/pixelTileWidth])){
						hitwall=true;
						break out;
					}
				}catch(IndexOutOfBoundsException e){}
			}
			//should also not hit wall if last one is in wall
			//////////////////////////////////////////
			//////////////////////////////////////////
			
			if(hitwall){
//				g.setColor(new Color(255,255,255));
//				g.drawLine(cen[0],cen[1],cen[0]+(int)(p.get(i)[0]),cen[1]+(int)(p.get(i)[1]));
				poly.add(new int[]{(int)(p.get(i)[0]),(int)(p.get(i)[1])});
				poly.add(new int[]{a.get(i)[0]-8,a.get(i)[1]-8});
//				g.drawOval((int)p.get(i)[0], (int)p.get(i)[1], 2, 2);
				//Hey as opposed to hitting the wall and drawing  apoint why not add bounds of wall
			}else{
				g.setColor(new Color(255,255,255,128));
				g.drawLine(cen[0], cen[1], (int)(cen[0]+xrange),(int)(cen[1]+yrange));
				poly.add(new int[]{(int)(xrange-16),(int)(yrange-16)});
				
			}
		}
		poly=MergeSortbyangle(poly);
		Polygon polygon = new Polygon();
		for(int[] r : poly){
			polygon.addPoint(r[0], r[1]);
		}
		g.translate(cen[0], cen[1]);
		g.drawPolygon(polygon);
		return dungeon;
	}
	
	private boolean arraycontains(ArrayList<int[]> a, int[] w) {
		for(int[] i : a){
			if (w.equals(i)){
				return true;
			}
		}
		return false;
	}
	private ArrayList<int[]> MergeSortbyangle(ArrayList<int[]> m) {
		if (m.size() <= 1)
			return m;
		ArrayList<int[]> left = new ArrayList<int[]>();
		ArrayList<int[]> right = new ArrayList<int[]>();
	    for (int i=0;i<m.size();i++){
	        if (i < (m.size())/2)
	            left.add(m.get(i));
	        else
	        	right.add(m.get(i));
	    }
	    left =MergeSortbyangle(left);
	    right = MergeSortbyangle(right);
	    ArrayList<int[]> result = new ArrayList<int[]>();
	    while (!left.isEmpty()&&!right.isEmpty())
	        if (atan0to2pi(left.get(0)) >= atan0to2pi(right.get(0))){
	            result.add(left.remove(0));
	        }else{
	        	result.add(right.remove(0));
	        }
	    while (!left.isEmpty())
	    	result.add(left.remove(0));
	    while (!right.isEmpty())
	    	result.add(right.remove(0));        	
	    return result;
	}
	public double atan0to2pi(int[] p){
		double t = Math.atan2(p[0], p[1]);
		if(t<0){
			t+=2*Math.PI;
		}
		return t;
	}
	private ArrayList<double[]> MergeSortbydistance(ArrayList<double[]> m) {
		if (m.size() <= 1)
			return m;
		ArrayList<double[]> left = new ArrayList<double[]>();
		ArrayList<double[]> right = new ArrayList<double[]>();
	    for (int i=0;i<m.size();i++){
	        if (i < (m.size())/2)
	            left.add(m.get(i));
	        else
	        	right.add(m.get(i));
	    }
	    left = MergeSortbydistance(left);
	    right = MergeSortbydistance(right);
	    ArrayList<double[]> result = new ArrayList<double[]>();
	    while (!left.isEmpty()&&!right.isEmpty())
	        if (Math.sqrt(Math.pow(left.get(0)[0], 2)+Math.pow(left.get(0)[1], 2)) <= Math.sqrt(Math.pow(right.get(0)[0], 2)+Math.pow(right.get(0)[1], 2))){
	            result.add(left.remove(0));
	        }else{
	        	result.add(right.remove(0));
	        }
	    while (!left.isEmpty())
	    	result.add(left.remove(0));
	    while (!right.isEmpty())
	    	result.add(right.remove(0));        	
	    return result;
	}
	private Image getImageFromTileID(int id) {
		if(id==0){
			return currentLevel.Void();
		}
		if(id==14){
			return currentLevel.Wall();
		}
		if(id==8||id==3/*this is the door id*/){
			return currentLevel.Floor();
		}
		if(id==10){
			return currentLevel.lowWall();
		}
		return null;
	}

	public void DrawGameOver(Graphics2D g2d, Point mousePosition) {

	}

	public void loadMapKit(String mapFile) {

	}
	public void loadTileset(String path) {
		URL tileSetUrl = this.getClass().getResource("/resources/images/"+path+".png");
		try {
			tileSet = ImageIO.read(tileSetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadDecorations(String path) {
		URL decorationSetUrl = this.getClass().getResource("/resources/images/"+path+".png");
		try {
			decorationSet = ImageIO.read(decorationSetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	boolean arraycontains(int[][] r,int w[]){
		for(int[] i : r){
			if (w.equals(i)){
				return true;
			}
		}
		return false;
	}
	Game(Player p){
		player=p;
		currentLevel =new Level(50,50,"testSpriteSheetforActors");
		Read.outputroom(currentLevel.roomids, "test");
		allEntities.add(p);
		player.x=(int) (25*pixelTileWidth);
		player.y=(int) (25*pixelTileWidth);
	}
	int slowdown=0;
	int slowdownfactor=1;
	void UpdateGame(Point mousePosition,boolean[] keyPressed,boolean[] mousePressed){
		for(Entity e :allEntities){
			tryLegalMovement(e,new int[]{e.vx,e.vy});
		}
		if(keyPressed[37/*left*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{-1,0});
		}
		if(keyPressed[38/*up*/]){
			if((slowdown%slowdownfactor)==0)
				tryLegalMovement(player,new int[]{0,-1});
		}
		if(keyPressed[39/*right*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{1,0});
		}
		if(keyPressed[40/*down*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{0,1});
		}
		slowdown++;
	}
	void tryLegalMovement(Entity e, int[] translation){
		if(legalMovement(e, translation)){
			e.x+=translation[0];
			e.y+=translation[1];
		}
	}

	boolean legalMovement(Entity e, int[] t){
		int xMin = (int) Math.floor((double)(e.getX()+t[0])/TILE_SIZE);
		int xMax = (int) Math.ceil ((double)(e.getX()+t[0]+e.getWidth ()-16)/TILE_SIZE);
		int yMin = (int) Math.floor((double)(e.getY()+t[1])/TILE_SIZE);
		int yMax = (int) Math.ceil ((double)(e.getY()+t[1]+e.getHeight()-16)/TILE_SIZE);
		for(int x=xMin;x<=xMax;x++){
			for(int y=yMin;y<=yMax;y++){
				try{
					if(Read.contains(e.invalidtiles, currentLevel.levellayout[x][y])){
						return false;
					}
				}
				catch(Exception ex){
					return false;
				}
			}
		}
		return true;
	}
}
