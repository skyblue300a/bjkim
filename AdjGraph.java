package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


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
		protected Vertex p;
		protected double d;
		protected double f;
		protected String id;
		protected int weight;
		protected HashMap<String, String> adj;
		
		Vertex(){
			this.color = 0;
			this.parent = null;
			this.d = 1E10;
			this.f = 1E10;
			this.id = null;
			this.weight = 0;
			this.adj = new HashMap();
		}
		
		Vertex(String id){
			this.color = 0;
			this.parent = null;
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
		HashMap<String, String> tmp = new HashMap<String, String>(v.adj);
		self.adj = tmp;
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
	int numFriend(String id){
		return v(id).weight;
	}
	boolean containsKey(String key){
		return this.v.containsKey(key);
	}
	
	ArrayList fiveSCC(){
		DFS d = new DFS(this);
		ArrayList<ArrayList<String>> a = new ArrayList();
		a = d.scc();
		
		DFSHEAP dh = new DFSHEAP(a);
		ArrayList<ArrayList<String>> res = new ArrayList();
		for(int i = 0; i < 5; i++){
			res.add(dh.extractMax());
		}
		
		return a;
	}

}
