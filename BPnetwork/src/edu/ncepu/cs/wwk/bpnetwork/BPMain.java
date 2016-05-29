package edu.ncepu.cs.wwk.bpnetwork;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BPMain {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		bpd = new BPData();
//		long pos = startNetwork(25);
//		bpd.readIncData("./data/data_filtered", 0, pos, 1);		
//	}
	
	public long startNetwork(BPData bpd,int basesample,String dataURL){
		System.out.println("BPnetwork begins...");
		System.out.println("Start at: "+new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss-SSS").format(new Date()));
		System.out.println("Reading data...");
		long pos  = bpd.readBaseData(dataURL,0, basesample);
		if(pos !=0){
			double p [][] = bpd.getPData();
			double t [][] = bpd.getTData();
			System.out.println("Data read!");
			System.out.println("Start training...");
			bpc = new BPConfig(p[0].length, 6, t[0].length,50);
			bpn = new BPNetwork(bpc);
			bpn.train(p, t, basesample);
		}else{
			System.out.println("error: Error code+ "+ pos);
		}
		System.out.println("End at: "+new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss-SSS").format(new Date()));
		return pos;
	}
	
	public void modelLearning(BPData bpd){
		System.out.println("inLearning begins...");
		System.out.println("Start at: "+new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss-SSS").format(new Date()));
		double p [][] = bpd.getpIncData();
		double t [][] = bpd.gettIncData();
		System.out.println("Training...");
		bpn.train(p, t, p.length);
		System.out.println("End at: "+new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss-SSS").format(new Date()));
	}
	
	public double[][] simModel(BPData bpd, boolean stop){
		double[][] p = null;
		double[][] t = null;
		if(stop)
		p = bpd.getPData();
		else
		p  = bpd.getpIncData();
		t = new double[p.length][1];
		for(int i = 0;i<p.length;i++){
		t[i] = bpn.sim(p[i]);
		}
		return t;
	}
	
	public BPConfig getBpc() {
		return bpc;
	}
	public BPNetwork getBpn() {
		return bpn;
	}
//	public BPData getBpd() {
//		return bpd;
//	}
	private BPConfig bpc;
	private BPNetwork bpn;
//	private BPData bpd;
}
