package edu.ncepu.cs.wwk.incframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncepu.cs.wwk.bpnetwork.BPData;
import edu.ncepu.cs.wwk.bpnetwork.BPMain;
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
	private long fileLength;
	private String data_url;
	private int count;
	/**
	 *<P><b>public IncFrame()</b></p><p>Default Construct Function. Instantiate an IncConfig object as the default configuration</p> 
  	 */
	public IncFrame(){
		super();
		initContext();
	}
	
	/**
	 *<P><b>public void initContext()</b></p><p>Initiate the incremental learning context with the default icf</p> 
	 */
	@Override
	public void initContext() {
		// TODO Auto-generated method stub
		icf = new IncConfig(1, 1, 25, 0.1, "BPNetwork");
		bpd = new BPData();
		bpm = new BPMain();
		data_url = "./data/testFile";
		pos = 0;
		current_t = null;
		stop = true;
		result_url = "./current_t";
		fileLength = (new File(data_url)).length();
		count = 0;
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
		bpm.startNetwork(bpd, icf.getBDV(),data_url);
		stop = false;
	}
		
	/**
	 *<P><b>public long performAnalysis()</b></p><p>Perform IncLearning</p> 
	 */
	@Override
	public void performAnalysis() {
		double[][] temp_result = bpm.simModel(bpd,stop);
		if(errorExceed(temp_result)){
		bpm.modelLearning(bpd);
		temp_result = bpm.simModel(bpd, stop);
		}
		if(!stop)
			current_p = bpd.getpIncData();
		else
			current_p = bpd.getPData();
		
		current_t = temp_result;
		// TODO Auto-generated method stub
	}
	
	private boolean errorExceed(double[][] t) {
		// TODO Auto-generated method stub
		double[][] t_origin = null;
		if(stop)
		t_origin = bpd.getTData();
		else
		t_origin = bpd.gettIncData();
		
		for(int i = 0;i<t.length;i++){
			if(Math.abs(t[i][0]-t_origin[i][0])>icf.getFR()){
				return true;
			}
		}
		return false;
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
				while(!stop){
				// TODO Auto-generated method stub
				pos = getIncData(pos);
				System.out.println(pos);
				if(pos >= fileLength){
					stop = true;
					continue;
				};
				performAnalysis();
				storeResult();
				count++;
				System.out.println("Start at: "+new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()));
				try {
					Thread.sleep(icf.getLTS());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		};
		return timeHandler;
	}
	
	private void storeResult() {
		// TODO Auto-generated method stub
		try {
			FileWriter fw = new FileWriter(result_url, true);
			String dataLine = "";
			for(int i = 0;i<current_t.length;i++){				
				for(int j = 0;j<current_p[0].length;j++){
					dataLine+=current_p[i][j]+" ";
				}
				dataLine += current_t[i][0]+"\r\n";
			}
			fw.write(dataLine);
			fw.flush();	
			fw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 *<P><b>public void setDataNext()</b></p><p>Move the data cursor to the next when the last epoch of training ends</p> 
	 */
	@Override
	public long getIncData(long pos) {
		// TODO Auto-generated method stub
		bpd.clearIncPData();
		bpd.clearIncTData();
		long current_pos = bpd.readIncData(data_url, 0, pos, icf.getDV());
		return current_pos;
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
	private String result_url;
	private long pos;
	private boolean stop;
	private double[][] current_p;
	private double[][] current_t;
	
}
