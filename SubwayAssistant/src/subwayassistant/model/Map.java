package subwayassistant.model;

import java.util.*;

public class Map {
	public int[][] getSubwayline() {
		return subwayline;
	}
	public void setSubwayline(int[][] subwayline) {
		this.subwayline = subwayline;
	}
	public List<String> getStations() {
		return stations;
	}
	public void setStations(List<String> stations) {
		this.stations = stations;
	}
	public int[][] getPath() {
		return path;
	}
	public void setPath(int[][] path) {
		this.path = path;
	}
	private int[][] subwayline;
	private static List<String> stations;
	private int[][] path;
	
	
	public int getIndex(String station) {//
		return stations.indexOf(station);
	}
	
	public String getName(int index){
		return stations.get(index);
	}
	
	public Map(List<String> stations) {//初始化整个地图
		this.stations = stations;
		this.subwayline = new int[stations.size()][stations.size()];
		this.path = new int[stations.size()][stations.size()];
		for(int i=0;i<stations.size();i++) {
			for(int j=0;j<stations.size();j++) {
				if(i==j) subwayline[i][j] = 0;
				else {
					subwayline[i][j] = 999999;
					subwayline[j][i] = 999999;
				}
			}
		}
	}
	
	public void initDis(String start, String end) {//初始化
		this.subwayline[getIndex(start)][getIndex(end)] = 1;
		this.subwayline[getIndex(end)][getIndex(start)] = 1;
	}
	
}
