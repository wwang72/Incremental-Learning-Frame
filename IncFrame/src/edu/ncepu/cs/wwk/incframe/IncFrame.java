package edu.ncepu.cs.wwk.incframe;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncepu.cs.wwk.bpnetwork.BPData;
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
		this.icf = new IncConfig();
	}
	/**
	 *<P><b>public IncFrame(IncConfig incconfig)</b></p><p>Construct Function with an object of IncConfig</p> 
	 */
	public IncFrame(IncConfig incconfig){
		super(); 
		this.icf = incconfig;
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
	 *<P><b>public void setIncConf(IncConfig incconfig)</b></p><p>Set the configuration of the incremental method dynamically</p> 
	 */
	@Override
	public void setIncConf(IncConfig incconfig) {
		// TODO Auto-generated method stub
		this.icf = incconfig;
	}
	
	/**
	 *<P><b>public void setIncConf(IncConfig incconfig)</b></p><p>Set the configuration of the incremental method dynamically</p> 
	 */
	@Override
	public int performAnalysis() {
		
		// TODO Auto-generated method stub
		return 0;
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
				performAnalysis();
				setDataNext();
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
	public void setDataNext() {
		// TODO Auto-generated method stub
		
	}
	private BPData bpd;
	private IncConfig icf;
	
}
