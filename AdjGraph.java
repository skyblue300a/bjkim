package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import a.Queue.*;

public class AdjGraph {
	private HashMap<String, Vertex> v;
	
	AdjGraph(){
		v = new HashMap();
	}
	
	HashMap graph(){
		return v;
	}
	
	public class Vertex{
		protected int color;
		protected String parent;
		protected String son;
		protected double d;
		protected double f;
		protected String id;
		private int weight;
		protected HashMap<String, String> adj;
		
		Vertex(){
			this.color = 0;
			this.parent = null;
			this.son = null;
			this.d = 1E10;
			this.f = 1E10;
			this.id = null;
			this.weight = 0;
			this.adj = new HashMap();
		}
		
		Vertex(String id){
			this.color = 0;
			this.parent = null;
			this.son = null;
			this.d = 1E10;
			this.f = 1E10;
			this.id = id;
			this.weight = 0;
			this.adj = new HashMap();
		}
		
		void VertexAdd(Vertex v){
			this.adj.put(v.id, v.id);
			v.weight++;
		}
		
		
		
		boolean containsAdj(Vertex v){
			return this.adj.containsKey(v.id);
		}
		
		
	}
	
	Vertex v(String id){
		return this.v.get(id);
	}
	
	void putVertex(Vertex v){
		this.v.put(v.id, v);
	}
	
	void putVertex(String id){
		Vertex v = new Vertex(id);
		putVertex(v);
	}
	
	void addVertex(String self, String friend){
		if(!v(self).containsAdj(v(friend))){
			v(self).VertexAdd(v(friend));
		}
	}
	
	void deleteVertex(String id){
		Iterator <String> i = v(id).adj.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			v(key).weight--;
		}
		i = v.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			if(v(key).adj.containsKey(id))
				v(key).adj.remove(id);
		}
		this.v.remove(id);
	}
	
	void VertexCopy(Vertex self, Vertex v){
		self.color = v.color;
		self.parent = v.parent;
		self.d = v.d;
		self.f = v.f;
		self.id = v.id;
		self.weight = v.weight;
		self.adj = v.adj;
	}
	
	Vertex newVertex(){
		return new Vertex();
	}
	
	
	Set findFriend(String id){
		return v(id).adj.keySet();
	}
	
	boolean containsFriend(String self, String friend){
		return v(self).adj.containsKey(friend);
	}
	
	boolean containsKey(String key){
		return this.v.containsKey(key);
	}
	
	ArrayList fiveSCC(){
		DFS d = new DFS(this);
		ArrayList<ArrayList<String>> a = new ArrayList();
		a = d.scc();
		
		int max[] = new int[5];
		int index[] = new int[5];
		for(int i = 0; i < 5; i++){
			max[i] = a.get(i).size();
			index[i] = i;
		}
		for(int i = 5; i < a.size(); i++){
			for(int j = 0; j < 5; j++){
				if(a.get(i).size() > max[j]){
					for(int k = 4; k > j; k++){
						max[k] = max[k - 1];
						index[k] = index[k - 1];
					}
					max[j] = a.get(i).size();
					index[j] = i;
				}
			}
		}
		ArrayList<ArrayList<String>> res = new ArrayList();
		for(int i = 0; i < 5; i++)
			res.add(a.get(index[i]));
		
		return res;
	}
}
