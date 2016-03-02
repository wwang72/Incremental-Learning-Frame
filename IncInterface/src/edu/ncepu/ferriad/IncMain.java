package edu.ncepu.ferriad;


public interface IncMain {
	
	public abstract void initContext();
	
	public abstract void baseMainStart();// the basis of the incremental learning
	
	public abstract void incMainStart();//the general entrance of incremental learning algorithm
	
	public abstract void setIncConf(IncConfig incconfig);//set the IncConfig of the class
	
	public abstract Runnable timeHandler();//control the machine learning algorithm for each dataLine
	
	public abstract long performAnalysis();//perform analysis for each dataLine
	
	public abstract void getIncData(long pos);//getIncData
	
	
}
