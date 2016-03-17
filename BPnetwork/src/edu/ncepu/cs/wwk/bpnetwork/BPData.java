package edu.ncepu.cs.wwk.bpnetwork;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BPData {
	private ArrayList<double[]> pBaseDataList;
	private ArrayList<Double> tBaseDataList;
	private ArrayList<double[]> pIncDataList;
	private ArrayList<Double> tIncDataList;
	private double [][] pBaseData;
	private double [][] tBaseData;
	private double [][] pIncData;
	private double [][] tIncData;
	
	public BPData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * <br>This is a method of BPData class. It reads pBaseData by two ways:</br>
	 * <br><b>type = 0</b> ---> read pBaseData from a file whose URL is url.</br>
	 * <br><b>type = 1</b> ---> read pBaseData from a directory whose URL is url.</br>
	 * <br>After the data are read, they are stored in pBaseData and tBaseData</br>
	 * <br>User can use getter method to obtain pdat aand tBaseData</br>
	 * <p><b>Returns</b></p>
	 * <p>0: training success</p>
	 * <p>x: training failed</p>
	 * @author Ferriad*/
	public long readBaseData(String url,int type, int lines){
		pBaseDataList = new ArrayList<double[]>();
		tBaseDataList = new ArrayList<Double>();
		long pos = 0;
		//File dataFilePackage = new File(".\\rawdata\\");
		if(type==0){
			File file = new File(url);
			pos = readBaseTxtFile(file,lines);
		}else if(type==1){
			File dataFilePackage = new File(url);
			File[] dataFiles = dataFilePackage.listFiles();
			for(File file: dataFiles){
				pos = readBaseTxtFile(file,lines);
			}
		}
		pBaseData = new double[pBaseDataList.size()][];
		for(int i = 0; i < pBaseData.length; i++){
			pBaseData[i]=pBaseDataList.get(i);
		}
		tBaseData = new double[tBaseDataList.size()][1];
		for(int i = 0; i < tBaseData.length; i++){
			tBaseData[i][0]=tBaseDataList.get(i);
		}
		//pBaseData = (double[][]) pBaseDataList.toArray();
		System.out.println("you chosed "+ pBaseData.length + "lines data as base data");
//		System.out.println(tBaseData.length);
		return pos;		
	}
	
	
	/**
	 * <br>This is a method of BPData class. It reads pBaseData by two ways:</br>
	 * <br><b>type = 0</b> ---> read pBaseData from a file whose URL is url.</br>
	 * <br><b>type = 1</b> ---> read pBaseData from a directory whose URL is url.</br>
	 * <br>After the data are read, they are stored in pBaseData and tBaseData</br>
	 * <br>User can use getter method to obtain pdat aand tBaseData</br>
	 * <p><b>Returns</b></p>
	 * <p>0: training success</p>
	 * <p>x: training failed</p>
	 * @author Ferriad*/
	public long readIncData(String url,int type, long pos, int lines){
		//File dataFilePackage = new File(".\\rawdata\\");
		pIncDataList = new ArrayList<double[]>();
		tIncDataList = new ArrayList<Double>();
		if(type==0){
			File file = new File(url);
			pos = readIncTextFile(file, pos, lines);
		}else if(type==1){
			File dataFilePackage = new File(url);
			File[] dataFiles = dataFilePackage.listFiles();
			for(File file: dataFiles){
				pos = readIncTextFile(file, pos, lines);
			}
		}
		pIncData = new double[pIncDataList.size()][];
		for(int i = 0; i < pIncData.length; i++){
			pIncData[i]=pIncDataList.get(i);
		}
		tIncData = new double[tIncDataList.size()][1];
		for(int i = 0; i < tIncData.length; i++){
			tIncData[i][0]=tIncDataList.get(i);
		}
		//pBaseData = (double[][]) pBaseDataList.toArray();
		System.out.println(pIncData.length + " line/lines data is/are selected as incremental data");
//		System.out.println(tIncData.length);
		return pos;		
	}
	
	@SuppressWarnings("resource")
	//the annotation part of restTxtFile is previously used to read data from the original .csv data 
	private long readBaseTxtFile(File file, int lines) {
		// TODO Auto-generated method stub
//		FileReader fileReader = null;
//		BufferedReader br = null;
		String dataLine = null;
		long lastPosition = 0;
		int lineCount = 0;
		try {
			RandomAccessFile rf = new RandomAccessFile(file, "r");
//			fileReader=new FileReader(file);
//			br=new BufferedReader(fileReader);
//			br.readLine();
//			String dataline = "";
			while((dataLine=rf.readLine()) != null && lineCount < lines){
				String[] strTemp = dataLine.split(",");
//				double[] pDataTemp = new double[strTemp.length-3];
				double[] pDataTemp = new double[strTemp.length-1];
				double tDataTemp = 0.00;
//				int j = 0;	
//				for(int i=0;i<strTemp.length;i++){
//					if(i<strTemp.length-2&&i!=1){
//				    	pDataTemp[j]=Double.parseDouble(strTemp[i]);
//				    	j++;
//					}else if(i==strTemp.length-1){
//						tDataTemp=Double.parseDouble(strTemp[i]);	
//					}
//				}
				for(int i=0;i<strTemp.length;i++){
					if(i<strTemp.length-1){
				    	pDataTemp[i]=Double.parseDouble(strTemp[i]);
					}else if(i==strTemp.length-1){
						tDataTemp=Double.parseDouble(strTemp[i]);	
					}
				}
				pBaseDataList.add(pDataTemp);
				tBaseDataList.add(tDataTemp);
				lineCount++;
				lastPosition = rf.getFilePointer();				
//				if(tDataTemp!=0.00&&pDataTemp.length==4){
//				pBaseDataList.add(pDataTemp);
//				tBaseDataList.add(tDataTemp);
////				dataline += pDataTemp[0] + "," + pDataTemp[1] + "," + pDataTemp[2] + "," + pDataTemp[3] + "," + tDataTemp + "\r\n";
//				}
			}
//			writeTextFile(dataline,"./data/data_filtered");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		} 
		return lastPosition;
	}
	
	@SuppressWarnings("resource")
	private long readIncTextFile(File file, long pos, int lines) {
		// TODO Auto-generated method stub

		String dataLine = null;
		long lastPosition = 0;
		int lineCount = 0;
		try {
			RandomAccessFile rf = new RandomAccessFile(file, "r");
			rf.seek(pos);
			while( (dataLine=rf.readLine()) != null && lineCount < lines){
				String[] strTemp = dataLine.split(",");
				double[] pDataTemp = new double[strTemp.length-1];
				double tDataTemp = 0.00;
				for(int i=0;i<strTemp.length;i++){
					if(i<strTemp.length-1){
				    	pDataTemp[i]=Double.parseDouble(strTemp[i]);
					}else if(i==strTemp.length-1){
						tDataTemp=Double.parseDouble(strTemp[i]);	
					}
				}
				pIncDataList.add(pDataTemp);
				tIncDataList.add(tDataTemp);
				lastPosition = rf.getFilePointer();
				lineCount++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -22;
		} 
		return lastPosition;
	}
	
	@SuppressWarnings("unused")
	private int writeTextFile(String dataLine, String filename) {
		int flag = 0;
//		FileOutputStream o = null;
		File dataFile = new File(filename);
		//createFile(city_file);
		try {
			    FileWriter fw = new FileWriter(dataFile,true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(dataLine);
	            bw.flush();
	            bw.close();
//			    fw.write(dataLine,0,dataLine.length());  
//    		    fw.flush(); 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = 1;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = 2;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = 3;
			}finally{
				;
			}
		return flag;
		// TODO Auto-generated method stub
		
	}
	public double[][] getPData() {
		return pBaseData;
	}

	public double[][] getTData() {
		return tBaseData;
	}
	
	public void clearBasePData(){
		this.pBaseData = null;
		this.pBaseDataList = null;
	}
	
	public void clearIncPData(){
		this.pIncData = null;
		this.pIncDataList = null;
	}

	public double[][] getpIncData() {
		return pIncData;
	}

	public double[][] gettIncData() {
		return tIncData;
	}
	
	public void clearBaseTData(){
		this.tBaseData = null;
		this.tBaseDataList = null;
	}
	
	public void clearIncTData(){
		this.tIncData = null;
		this.tIncDataList = null;
	}
	
	
}
