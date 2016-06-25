package a;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import a.AdjGraph.*;

public class Dijsktra {
	private HashMap<String, Vertex> init;
	//private Vertex[] v;
	private PriorityQueue<Vertex> q;
	
	Dijsktra(AdjGraph g){
		this.init = new HashMap(g.graph());
		Iterator<String> i = this.init.keySet().iterator();
		while(i.hasNext()){
			String key = i.next();
			this.init(key).color = 0;
			this.init(key).p = null;
			this.init(key).d = 1E10; //여기선 d를 거리로 쓰자
		}
		Comparator<Vertex> c = new distanceComparator();
		this.q = new PriorityQueue<Vertex>(c);
	}
	Vertex init(String id){
		return this.init.get(id);
	}
	
	ArrayList<Vertex> dij(String id){
		ArrayList<Vertex> a = new ArrayList();
		init(id).d = 0;
		q.add(init(id));
		while(!q.isEmpty()){
			Vertex tmp = q.poll();
			tmp.color = 1;
			a.add(tmp);
			Iterator<String> i = init(tmp.id).adj.keySet().iterator();
			while(i.hasNext()){
				String key = i.next();
				if(init(key).color == 0){
					if(init(key).d > tmp.d + init(key).weight){
						init(key).d = tmp.d + init(key).weight;
						init(key).p = tmp;
					}
					if(!q.contains(init(key)))
						q.add(init(key));
				}
			}
			
		}
		return a;
	}
	
	public class distanceComparator implements Comparator<Vertex>{
		@Override
		public int compare(Vertex a, Vertex b){
			if(a.d > b.d)
				return -1;
			else if(a.d < b.d)
				return 1;
			return 0;
		}
	}
	
	
	
	
	
}
