package crungeonDawler;
import java.io.File;
import java.util.ArrayList;

public class Level {
	//Not Today but Plan:
	//Start is center -Rand room or special center room
	//on each "entrance" -need special tile for this -not sure how multiple wide doors will work
	//try and place random room, if it fits then place it and continue else try
	//another room type, if all rooms are invalid give up and replace with wall
	
	//also need to have outer bound, probably just square around level
	
	
	//For Tommorow: idk do something
	
	//will give the level layout, room wise atleast in a bufferedImage
	
	//currently I am going to treat all rooms as rectangles, to not have rectangle room,
	//have a larger rect the bounds a non rect room with void around it
	
	public static void main(String[] tf){
		Level level=new Level(100,100);
		Read.outputroom(level.level, "map output");
	}
	static final int doorID = 2;
	static ArrayList<int[][]> allroomdesigns =new ArrayList<int[][]>();
	int[][] level;
	int[][] roomids;
	Level(int width, int height){
		allroomdesigns.clear();
		for(File f : new File("Roomdesigns").listFiles()){
			allroomdesigns.add(Read.readimagefile(f));
		}
		level = new int[width][height];
		roomids = new int[width][height];
		level[width/2][height/2]=doorID;
		while(!done()){
			ArrayList<int[]> doors = new ArrayList<int[]>();
			for(int i=0;i<level.length;i++){
				for(int j=0;j<level[i].length;j++){
					if(level[i][j]==doorID){
						doors.add(new int[]{i,j});
					}
				}
			}
			int[] nextdoor = doors.get((int) (doors.size()*Math.random()));
			ArrayList<int[][]> rooms = (ArrayList<int[][]>) allroomdesigns.clone();
			boolean roomplaced=false;
			while (rooms.size()>0&!roomplaced){
				int[][] fr = rooms.remove((int)(Math.random()*rooms.size()));
				roomplaced=placeroom(fr,level,roomids,nextdoor);
			}
			level[nextdoor[0]][nextdoor[1]]=3;
		}
		
		for(int i=0;i<level.length;i++){
			for(int j=0;j<level[i].length;j++){
				if(level[i][j]==3){
					try{
						if(!((level[i-1][j]==8&&level[i+1][j]==8)||(level[i][j-1]==8&&level[i][j+1]==8))){
							level[i][j]=14;
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
		}
		for(int i=0;i<level.length;i++){
			for(int j=0;j<level[i].length;j++){
				if(level[i][j]==14){
					try{
						if(level[i+1][j]==8){
							level[i+1][j]=10;
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
		}
	}
	static int nextroomid = 1;
	private boolean placeroom(int[][] r, int[][] level,int[][] roomids,int[] placeon) {
		boolean everok=false;
		for(int rot=0;rot<4;rot++){
			ArrayList<int[]> doors = new ArrayList<int[]>();
			for(int i=0;i<r.length;i++){
				for(int j=0;j<r[i].length;j++){
					if(r[i][j]==doorID){
						doors.add(new int[]{i,j});
					}
				}
			}
			for(int[] f : doors){
				boolean ok=true;
				for(int i=0;i<r.length;i++){
					for(int j=0;j<r[i].length;j++){
						try{
							if(!(level[placeon[0]-f[0]+i][placeon[1]-f[1]+j]==Read.defaultID||level[placeon[0]-f[0]+i][placeon[1]-f[1]+j]==r[i][j]||r[i][j]==Read.defaultID)){
								ok=false;
							}
						}
						catch(ArrayIndexOutOfBoundsException e){ok=false;}
					}
				}
				everok|=ok;
				if(ok){
					for(int i=0;i<r.length;i++){
						for(int j=0;j<r[i].length;j++){
							if(r[i][j]!=0){
								level[placeon[0]-f[0]+i][placeon[1]-f[1]+j]=r[i][j];
							}
						}
					}
					for(int i=0;i<r.length;i++){
						for(int j=0;j<r[i].length;j++){
							if(r[i][j]!=0){
								roomids[placeon[0]-f[0]+i][placeon[1]-f[1]+j]=nextroomid;
							}
						}
					}
				}
			}
			r=rotate(r);
		}
		return everok;
	}
	static int[][] rotate(int[][] red){//just rotates clockwise
		int[][] temp = new int[red[0].length][];
		for (int x = 0;x<red[0].length;x++){
			temp[x]=new int[red.length];
			for (int y=0;y<red.length;y++){
				temp[x][y]=red[y][x];
			}
		}
		int[][]ret = new int[red[0].length][];
		for (int x = 0;x<temp.length;x++){
			ret[x]=new int[red.length];
			for (int y=0;y<temp[x].length;y++){
				//flip rows or columns//[x][ret[0].length-y-1] for clockwise, [ret.length-x][y] for counter
				ret[x][y]=temp[x][ret[0].length-y-1];
			}
		}
		return ret;
	}
	private boolean done() {
		for(int i=0;i<level.length;i++){
			for(int j=0;j<level.length;j++){
				if(level[i][j]==doorID){
					return false;
				}
			}
		}
		return true;
	}
	
}
