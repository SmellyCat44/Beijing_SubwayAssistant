package subwayassistant.model;

import java.util.*;

public class Result {
	public int[][] getShortestPath() {
		return ShortestPath;
	}

	public void setShortestPath(int[][] shortestPath) {
		ShortestPath = shortestPath;
	}

	public int[][] getShortestDis() {
		return ShortestDis;
	}

	public void setShortestDis(int[][] shortestDis) {
		ShortestDis = shortestDis;
	}

	public static int getMax() {
		return max;
	}

	private static final int max = 999999;
	private int[][] ShortestPath;
	private int[][] ShortestDis;
	
	public Result(int[][] G) {
		this.ShortestPath = new int[G.length][G.length];
		this.ShortestDis = new int[G.length][G.length];
		for(int i=0;i<G.length;i++) {
			for(int j=0;j<G.length;j++) {
				this.ShortestPath[i][j]=j;
				this.ShortestDis[i][j]=G[i][j];
				this.ShortestDis[j][i]=G[j][i];
			}
		}
		
		//FloydºËÐÄËã·¨
		for(int k=0;k<G.length;k++)
			for(int j=0;j<G.length;j++)
				for(int i=0;i<G.length;i++) {
					if(this.ShortestDis[i][j]>this.ShortestDis[i][k]+this.ShortestDis[k][j]) {
						this.ShortestDis[i][j]=this.ShortestDis[i][k]+this.ShortestDis[k][j];
						this.ShortestPath[i][j]=this.ShortestPath[i][k];
					}
				}
	}
	
	public int getMinDis(int i, int j) {
		return this.ShortestDis[i][j];
	}
	
	public List<Integer> indexToList(int i, int j){
		List<Integer> list = new ArrayList<>();
		while(i!=j) {
			list.add(i);
			i = this.ShortestPath[i][j];
		}
		list.add(i);
		return list;
	}
}
