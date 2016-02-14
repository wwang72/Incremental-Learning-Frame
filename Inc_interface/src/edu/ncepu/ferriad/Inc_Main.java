package edu.ncepu.ferriad;
public class Inc_Main {
	public Inc_Main(Inc_Config ic) {
		super();
		this.ic = ic;
	}
	public int Inc_MainStart() throws InterruptedException{
		if(ic.getLA().equals("bpnetwork")){
			while(true){
				//The main function would sleep for a time
				//inc-learning require the data be continuously taken in
				
				//bptraining
				Thread.sleep(ic.getLTS());
			}				
		}
		return 0;
	}
	private Inc_Config ic;
}
