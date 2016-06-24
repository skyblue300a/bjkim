package a;

import java.util.HashMap;
import java.util.Iterator;

import a.AdjGraph.*;

public class DFSHEAP {
    private Vertex[] vset;
    private int sz;
    private AdjGraph g;
    
    DFSHEAP(HashMap<String, Vertex> h){
    	sz = h.size();
    	vset = new Vertex[sz];
    	Iterator<String> i = h.keySet().iterator();
    	int cnt = 0;
    	while(i.hasNext()){
    		String key = i.next();
    		vset[cnt] = h.get(key);
    	}
    }
    

	int left(int i){
    	return 2*i + 1;
    }
    int right(int i){
    	return 2*i + 2;
    }
    void heapify(int i, int sz){
    	int lar;
    	int l = this.left(i);
    	int r = this.right(i);
    	if(l < sz && vset[i].f < vset[l].f)
    		lar = l;
    	else
    		lar = i;
    	if(r < sz && vset[lar].f < vset[r].f)
    		lar = r;
    	if(lar != i){
    		Vertex tmp = g.newVertex();
    		tmp = vset[lar];
    		vset[lar] = vset[i];
    		vset[i] = tmp;
    		this.heapify(lar, sz);
    	}
    }
    void buildheap(){
    	for(int i = (sz / 2) + 1; i >= 0; i--){
    		heapify(i - 1, sz);
    	}
    }
    
    void heapsort(){
    	buildheap();
    	for(int i = sz; i > 0; i--){
    		Vertex tmp = g.newVertex();
    		tmp = vset[i-1];
    		vset[i-1] = vset[0];
    		vset[0] = tmp;
    		heapify(0, i - 1);
    	}
    }
    
    Vertex[] sort_by_f(){
    	heapsort();
    	return vset;
    }
}