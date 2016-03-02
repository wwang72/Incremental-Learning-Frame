package edu.ncepu.cs.wwk.incframe;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncepu.cs.wwk.bpnetwork.BPData;
import edu.ncepu.cs.wwk.bpnetwork.BPMain;
import edu.ncepu.cs.wwk.bpnetwork.BPNetwork;
import edu.ncepu.ferriad.*;
/**
 * <p><b>Incremental Learning Frame</b></p>
 * <p>Description: </p>
 * <p>implement the IncInterface interface and build the incremental learning frame available for some machine learning algorithms.</p>
 * <p>Institute: North China Electric Power Uiversity</p>
 * @author Weikang Wang
 * @version 1.0
 */
public class IncFrame implements IncMain{
	
	/**
	 *<P><b>public IncFrame()</b></p><p>Default Construct Function. Instantiate an IncConfig object as the default configuration</p> 
  	 */
	public IncFrame(){
		super();
	}
	
	/**
	 *<P><b>public void initContext()</b></p><p>Initiate the incremental learning context with the default icf</p> 
	 */
	@Override
	public void initContext() {
		// TODO Auto-generated method stub
		icf = new IncConfig(1, 2, 25, 0.01, "BPNetwork");
		bpd = new BPData();
		bpm = new BPMain();
	}
	/**
	 *<P><b>public void incMainStart()</b></p><p>Start the incremental learning method in multi-thread mod</p> 
	 */
	@Override
	public void incMainStart() {
		// TODO Auto-generated method stub
		Thread incThread = new Thread(timeHandler());
		incThread.start();
		
	}
	/**
	 *<P><b>public void baseMainStart()</b></p><p>Start the incremental learning basic part</p> 
	 */
	@Override
	public void baseMainStart() {
		// TODO Auto-generated method stub
		bpm.startNetwork(bpd, icf.getBDV());
	}
		
	/**
	 *<P><b>public long performAnalysis()</b></p><p>Perform IncLearning</p> 
	 */
	@Override
	public long performAnalysis() {
		long lastPos = bpm.incLearning(bpd, pos, icf.getDV());
		// TODO Auto-generated method stub
		return lastPos;
	}
	
	/**
	 *<P><b>public Runnable timeHandler()</b></p><p>Create the timeHandler that does work during each epoch</p> 
	 */
	@Override
	public Runnable timeHandler() {
		// TODO Auto-generated method stub
		Runnable timeHandler = new Runnable() { 
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pos = performAnalysis();
				getIncData(pos);
				System.out.println("Start at: "+new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()));
			try {
				Thread.sleep(icf.getLTS());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		};
		return timeHandler;
	}
	
	/**
	 *<P><b>public void setDataNext()</b></p><p>Move the data cursor to the next when the last epoch of training ends</p> 
	 */
	@Override
	public void getIncData(long pos) {
		// TODO Auto-generated method stub
		bpd.clearIncPData();
		bpd.clearIncTData();
		bpd.readIncData("./data/data_filtered", 0, pos, icf.getDV());	
	}
	

	/**
	 *<P><b>public void setIncConf(IncConfig incconfig)</b></p><p>Set the configuration of the incremental method dynamically</p> 
	 */
	@Override
	public void setIncConf(IncConfig incconfig) {
		// TODO Auto-generated method stub
		this.icf = incconfig;
	}
	private BPData bpd;
	private BPMain bpm;
	private IncConfig icf;
	private long pos;
	
	
}
