package crungeonDawler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
	public static final int renderdist = 20;
	public void Draw(Graphics2D g2d, Point mousePosition) {
		//Hey Just want to note that the black lines flashing onto the screen
		//only appear moving left or up, never down or right
		//because as we new it begins redrawing before being finished before
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		for(int x=Math.max(player.getX()/pixelTileWidth-renderdist,0);x<currentLevel.width&&x<player.getX()/pixelTileWidth+renderdist+1;x++){
			for(int y=Math.max(player.getY()/pixelTileWidth-renderdist,0);y<currentLevel.height&&y<player.getY()/pixelTileWidth+renderdist+1;y++){
				dungeon.getGraphics().drawImage(getImageFromTileID(currentLevel.levellayout[x][y]),x*pixelTileWidth-player.getX()+dungeon.getWidth()/2,y*pixelTileWidth-player.getY()+dungeon.getHeight()/2, pixelTileWidth, pixelTileWidth,null);
			}
		}
		
		for(Entity e : allEntities){
			if(Math.abs(e.getX()-player.getX())<renderdist&& Math.abs(e.getY()-player.getY())<renderdist){
				dungeon.getGraphics().drawImage(e.getSprite(), (e.getX()-player.getX())+dungeon.getWidth()/2, (e.getY()-player.getY())+dungeon.getHeight()/2,e.getWidth(),e.getHeight(), null);
			}
		}
//		dungeon.getGraphics().drawImage(getVisible(), 0, 0, null);
		BufferedImage vis = getVisible();
//		System.out.println("w"+Color.WHITE.getRGB());
//		System.out.println(Color.black.getRGB());
		for(int x=0;x<dungeon.getWidth();x++){
			for(int y=0;y<dungeon.getHeight();y++){
				if(vis.getRGB(x, y)!=-1){
					dungeon.setRGB(x, y, -16777216);
				}
			}
		}
		g2d.drawImage(dungeon, (Screen.frameWidth-dungeon.getWidth())/2,(Screen.frameHeight-dungeon.getHeight())/2,null);
	}
	public BufferedImage getVisible() {
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_3BYTE_BGR); 
		Graphics2D g = (Graphics2D)dungeon.getGraphics();
		g.setStroke(new BasicStroke(12,2,2));
		g.translate(-player.x+dungeon.getWidth()/2, -player.y+dungeon.getHeight()/2);
		for(double theta=0;theta<Math.PI*2;theta+=Math.PI*2/120){
			double xrange = Math.cos(theta)*player.lengthOfLineOfSight*pixelTileWidth;
			double yrange = Math.sin(theta)*player.lengthOfLineOfSight*pixelTileWidth;
			double m = Math.tan(theta);
			ArrayList<double[]> p = new ArrayList<double[]>();
			int[] testcenter = new int[]{(int)((player.x)/pixelTileWidth)*pixelTileWidth,(int)((player.y)/pixelTileWidth)*pixelTileWidth};
//			g.setColor(Color.white);
//			for(int i=-10;i<11;i++){
//				g.drawLine(testcenter[0]-10*pixelTileWidth, testcenter[1]+i*pixelTileWidth, testcenter[0]+10*pixelTileWidth, testcenter[1]+i*pixelTileWidth);
//				g.drawLine(testcenter[0]+i*pixelTileWidth, testcenter[1]-10*pixelTileWidth, testcenter[0]+i*pixelTileWidth, testcenter[1]+10*pixelTileWidth);
//			}
			int[] cen = new int[]{player.x+8,player.y+8};
			for(double i=0;Math.abs(i)<Math.abs(xrange);i+=Math.signum(xrange)*pixelTileWidth){
				p.add(new double[]{i-cen[0]%16, m*(i-cen[0]%16+8)});
			}
			for(int i=0;Math.abs(i)<Math.abs(yrange);i+=Math.signum(yrange)*pixelTileWidth){
				p.add(new double[]{(i-cen[1]%16+8)/m,i-cen[1]%16});
			}
			p=MergeSort(p);
			for(int i=0;i<p.size();i++){
				if(Math.sqrt(Math.pow(p.get(i)[0],2)+Math.pow(p.get(i)[1], 2))>pixelTileWidth*(player.lengthOfLineOfSight+1)){
					p.remove(i);
					i--;
				}
			}
			
			
			//////////////////
			//EVERYTHING BEYOND THIS POINT DOESN"T WORK
			
			
			
			///////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////
			//THIS CODE TRIES TO FIGURE OUT WHAT BOXES NEED TO BE CHECKED FOR EACH GRID LINE INTERSECTION
			//I HAVE NO IDEA HOW THE MATH SHOULD ACTUALLY BE DONE AND THIS IS ONE OF THE LARGE SOURCES OF TERRIBLENESS
			
			
			//also a is always 0
			ArrayList<int[]> a = new ArrayList<int[]>();
			for(int i=0;i<p.size();i++){
				if(p.get(i)[0]-(int)p.get(i)[0]==0d){
//					if(!arraycontains(a,new int[]{Read.roundto(p.get(i)[0]+8,16),Read.roundto(p.get(i)[1]+8,16)}))
//					System.out.println(Read.roundto(p.get(i)[0]+8,16)+" "+Read.roundto(p.get(i)[1]+8,16));
						a.add(new int[]{Read.roundto(p.get(i)[0]+8,16),Read.roundto(p.get(i)[1]+8,16)});
				}else{
//					if(!arraycontains(a,new int[]{Read.roundto(p.get(i)[0]+8,16),Read.roundto(p.get(i)[1]+8,16)}))
						a.add(new int[]{Read.roundto(p.get(i)[0]+8,16),Read.roundto(p.get(i)[1]+8,16)});
				}
			}
			//////////////////////////////////////////
			//////////////////////////////////////////
			
			
			boolean hitwall=false;
			int i=0;
			out:
			for(;i<a.size();i++){
//				System.out.println((a.get(i)[0]/pixelTileWidth+" "+Read.roundto(player.x, 16)/pixelTileWidth));
//				System.out.println((a.get(i)[0]+Read.roundto(player.x, 16))/pixelTileWidth+" "+(a.get(i)[1]+Read.roundto(player.y, 16))/pixelTileWidth);
				//needs to have low wall be transparent
				if(Read.contains(player.invalidtiles,currentLevel.levellayout[(a.get(i)[0]+Read.roundto(player.x, 16))/pixelTileWidth][(a.get(i)[1]+Read.roundto(player.y, 16))/pixelTileWidth])){
					hitwall=true;
					break out;
				}
			}
			g.setColor(new Color(255,255,255));
			
			if(hitwall){
				g.drawLine(cen[0],cen[1],cen[0]+(int)(p.get(i)[0]),cen[1]+(int)(p.get(i)[1]));
//				g.drawLine(cen[0], cen[1], (int)(cen[0]+xrange),(int)(cen[1]+yrange));
			}else{
				g.drawLine(cen[0], cen[1], (int)(cen[0]+xrange),(int)(cen[1]+yrange));
			}
//			g.setColor(Color.blue);
//			for(int j=0;j<a.size();j++){
//				g.fillRect((int)(testcenter[0]+a.get(j)[0]), (int)(testcenter[1]+a.get(j)[1]), 16, 16);
//			}
//			for(int j=0;j<p.size();j++){
//				g.setColor(new Color(255,255,255));
//				g.drawLine(cen[0],cen[1],cen[0]+(int)(p.get(j)[0]),cen[1]+(int)(p.get(j)[1]));
//				
//				g.setColor(Color.blue);
//				g.drawOval(cen[0]+(int)(p.get(j)[0])-1,cen[1]+(int)(p.get(j)[1])-1, 2, 2);
//			}
			
			
			//using that information draw the line to go into the hitbox of but not through walls.
			//so far points are not circular, need to make it so that if all points hit nothing line is not drawn to point instead drawn all
		}
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
	private ArrayList<double[]> MergeSort(ArrayList<double[]> m) {
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
	    left = MergeSort(left);
	    right = MergeSort(right);
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
		currentLevel =new Level(100,100,"testSpriteSheetforActors");
		allEntities.add(p);
		player.x=(int) (50*pixelTileWidth);
		player.y=(int) (50*pixelTileWidth);
	}
	int slowdown=0;
	int slowdownfactor=1;
	void UpdateGame(Point mousePosition,boolean[] keyPressed,boolean[] mousePressed){
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
