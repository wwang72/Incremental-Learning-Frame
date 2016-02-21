package edu.ncepu.ferriad;
public interface IncMain {
	public abstract void incMainStart(IncConfig icf);//the general entrance of incremental learning algorithm
	
	public abstract void setIncConf(IncConfig icf);//set the IncConfig of the class
	
	public abstract Runnable timeHandler();//control the machine learning algorithm for each dataLine
	
	public abstract void performAnalysis();//perform analysis for each dataLine
	
	
}
