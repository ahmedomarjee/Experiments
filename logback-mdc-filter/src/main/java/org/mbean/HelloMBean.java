package org.mbean;

public interface HelloMBean { 
	 
    public void sayHello(); 
    public int add(int x, int y); 
    public void setFilter(String filterName, String filterValue);
    
    public String getName(); 
     
    public int getCacheSize(); 
    public void setCacheSize(int size); 
} 