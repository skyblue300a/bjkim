package a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import a.AdjGraph.*;

public class DFSHEAP {
    private Vertex[] vset;
    private Vertex[] minvset;
    private int sz;
    private AdjGraph g;
    private ArrayList<ArrayList<String>> a;
    
    DFSHEAP(ArrayList<ArrayList<String>> a){
    	this.a = a;
    	this.sz = a.size();
    	this.buildheap(this.a);
    }
    
    DFSHEAP(HashMap<String, Vertex> h){
    	sz = h.size();
    	vset = new Vertex[sz];
    	minvset = new Vertex[sz];
    	Iterator<String> i = h.keySet().iterator();
    	int cnt = 0;
    	while(i.hasNext()){
    		String key = i.next();
    		vset[cnt] = minvset[cnt] = h.get(key);
    		cnt++;
    	}
    }
    

	int left(int i){
    	return 2*i + 1;
    }
    int right(int i){
    	return 2*i + 2;
    }
    
    void heapify(ArrayList<ArrayList<String>> a, int i, int sz){
    	int s;
    	int l = this.left(i);
    	int r = this.right(i);
    	if(l < sz && a.get(i).size() > a.get(l).size())
    		s = l;
    	else
    		s = i;
    	if(r < sz && a.get(s).size() > a.get(r).size())
    		s = r;
    	if(s != i){
    		ArrayList<String> tmp = a.get(s);
    		a.remove(s);
    		a.add(s, a.get(i));
    		a.remove(i);
    		a.add(i,tmp);
    		
    		this.heapify(a, s, sz);
    	}
    }
    void buildheap(ArrayList<ArrayList<String>> a){
    	for(int i = (sz / 2) + 1; i > 0; i--){
    		heapify(a, i - 1, sz);
    	}
    }

    ArrayList<String> extractMax(){
    	ArrayList<String> tmp = a.get(sz - 1);
		a.remove(sz - 1);
		a.add(sz - 1, a.get(0));
		a.remove(0);
		a.add(0,tmp);
		heapify(a, 0, --sz);
		return tmp;
    }
    
    
    void heapify(int i, int sz){
    	int s;
    	int l = this.left(i);
    	int r = this.right(i);
    	if(l < sz && vset[i].f > vset[l].f)
    		s = l;
    	else
    		s = i;
    	if(r < sz && vset[s].f > vset[r].f)
    		s = r;
    	if(s != i){
    		Vertex tmp = vset[s];
    		vset[s] = vset[i];
    		vset[i] = tmp;
    		this.heapify(s, sz);
    	}
    }
   
    void buildheap(){
    	for(int i = (sz / 2) + 1; i > 0; i--){
    		heapify(i - 1, sz);
    	}
    }

    
    void heapsort(){
    	buildheap();
    	for(int i = sz; i > 1; i--){
    		Vertex tmp = vset[i-1];
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