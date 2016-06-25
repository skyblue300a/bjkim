package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import a.AdjGraph.*;

public class DFS {
	private final int WHITE = 0, BLACK = 1;
	private HashMap<String, Vertex> v;
	private int time;
	private AdjGraph g;
	private ArrayList <String> a;
	
	DFS(AdjGraph g){
		this.v = g.graph();
		this.time = 0;
		this.g = g;
		this.a = new ArrayList();
	}
	
	Vertex v(String id){
		return this.v.get(id);
	}
	
	void dfs(){
		Iterator<String> i = this.v.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			v(key).color = WHITE;
			v(key).parent = null;
		}
		i = this.v.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			if(v(key).color == WHITE){
				this.dfs_visit(v(key));
			}
		}
	}
	void dfs_visit(Vertex v){
		v.d = ++this.time;
		v.color = BLACK;
		Iterator<String> j = v.adj.keySet().iterator();
		while(j.hasNext()){
			String key = j.next();
			if(v(key).color == WHITE){
				v(key).parent = v.id;
				this.dfs_visit(v(key));
			}
		}
		v.f = ++this.time;
	}
	
	void transpose(){
		HashMap <String, Vertex> u = new HashMap();
		Iterator<String> i = this.v.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			Vertex tmp = g.newVertex();
			g.VertexCopy(tmp, v(key));
			u.put(key, tmp);
		}
		i = u.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			u.get(key).adj.clear();
		}
		i = this.v.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			Iterator<String> j = v(key).adj.keySet().iterator();
			while(j.hasNext()){
				String ad = j.next();
				u.get(ad).adj.put(key, key);
			}
		}
		this.v.clear();
		this.v = new HashMap(u);
	}
	
	void scc_find(Vertex v){
		a.add(v.id);
		v.color = BLACK;
		Iterator<String> i = v.adj.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			if(v(key).color == WHITE){
				v(key).parent = v.id;
				this.scc_find(v(key));
			}
		}
	}
	
	ArrayList scc(){
		this.dfs();
		this.transpose();
		DFSHEAP dh = new DFSHEAP(v);
		Vertex s[] = dh.sort_by_f();
		for(int i = 0; i < v.size(); i++){
			s[i].color = WHITE;
			s[i].parent = null;
		}

		ArrayList<ArrayList<String>> x = new ArrayList();
		for(int i = 0; i < v.size(); i++){
			if(s[i].color == WHITE){
				a.clear();
				scc_find(s[i]);
				ArrayList<String> tmp = new ArrayList(a);
				x.add(tmp);
			}
		}

	
		return x;
	}
}
