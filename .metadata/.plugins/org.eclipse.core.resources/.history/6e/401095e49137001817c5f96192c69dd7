import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Read {
	public static void main(String[] re) throws IOException{
//		int[][] r = readcsvfile(new File("src/csvtest.txt"));
		int[][] r = readimagefile(new File("untitled.png"));
		for(int k=0;k<r.length;k++){
			for(int t=0;t<r[k].length;t++){
				System.out.print(r[k][t]+" ");
			}
			System.out.println();
		}
		outputroom(r,"writetest");
	}
	static int[] colorRGB = new int[]{-1237980,-32985,-3584,-4856291,-6694422,-16735512,-20791,-1,-12629812,-3620889,-4621737,-7864299,-16777216,-8421505,-3947581,-14503604,-6075996,-14066,-1055568,-9399618};
	static int[] colorID = new int[]{2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
	//above needs to be done some other way, but here is the default paint colors
	//in order: red,orange,yellow,lime,light turquoise,turquoise,rose,white,Indigo,lavender,brown,dark red,black,grey 50%,grey 25%,Green,purple,gold,light yellow,blue-grey
	static int defaultID = 1;
	static int voidRGB = Integer.MIN_VALUE;
	static int ColortoID(Color col){
		for(int i=0;i<colorID.length;i++){
			if(colorRGB[i]==col.getRGB()){
				return colorID[i];
			}
		}
		System.out.println(col.getRGB());
		return defaultID;
	}
	private static int IDtoColor(int id) {
		for(int i=0;i<colorRGB.length;i++){
			if(colorID[i]==id){
				return colorRGB[i];
			}
		}
		System.out.println(id);
		return voidRGB;
	}
	//OK the above is declared in code right now, but it can be changed to be read later, but that will require
	//making the methods below not static
	static int[][] readcsvfile(File f){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String r = reader.readLine();
			ArrayList<ArrayList<Integer>> parse = new ArrayList<ArrayList<Integer>>();
			int i=0;
			int j=0;
			String s="";
			while(r!=null){
				parse.add(new ArrayList<Integer>());
				for(int k=0;k<r.length();k++){
					if(!r.substring(k, k+1).equals(",")){
						s+=r.substring(k, k+1);
					}else{
						j++;
						try{
							parse.get(i).add(Integer.parseInt(s));
						}catch(Exception e){
							System.out.println("error1 at "+i+","+j+"   "+s);
						}
						s="";
						
					}
				}
				try{
					parse.get(i).add(Integer.parseInt(s));
				}catch(Exception e){
					System.out.println("error at "+i+","+j+"   "+s);
				}
				r = reader.readLine();
				s="";
				i++;
				j=0;
			}
			try{
				parse.get(i-1).add(Integer.parseInt(s));
			}catch(Exception e){}
			int[][] array=new int[parse.size()][];
			for(int k=0;k<parse.size();k++){
				array[k]=new int[parse.get(k).size()];
				for(int t=0;t<parse.get(k).size();t++){
					array[k][t]=parse.get(k).get(t);
				}
			}
			return array;
		} catch (Exception e) {
			return null;
		}
	}
	static int[][] readimagefile(File f){
		BufferedImage img;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		int[][] result = new int[img.getHeight()][];
		for(int x=0;x<img.getHeight();x++)
			result[x]=new int[img.getWidth()];
		for(int x=0;x<img.getWidth();x++){
			for(int y=0;y<img.getHeight();y++){
				result[y][x]=ColortoID(RGBtocolor(img.getRGB(x, y)));
			}
		}
		return result;
	}
	static Color RGBtocolor(int rgb){
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;
		return new Color(red,green,blue);
	}
	static void outputroom(int[][] room,String name){//just outputs to project folder
		int height=room[0].length;
		for(int[] r :room){
			height=Math.max(height, r.length);
		}
		BufferedImage b = new BufferedImage(height,room.length,BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<room.length;x++){
			for(int y=0;y<room[x].length;y++){
				b.setRGB(y, x, IDtoColor(room[x][y]));
			}
		}
		try {
		    File outputfile = new File(name+".png");
		    ImageIO.write(b, "png", outputfile);
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
	
}
