package edu.ncepu.ferriad;
public class IncConfig {
	
	public int getLTS() {
		return LTS;
	}
	public void setLTS(int lTS) {
		LTS = lTS;
	}
	public int getDV() {
		return DV;
	}
	public void setDV(int dV) {
		DV = dV;
	}
	public double getFR() {
		return FR;
	}
	public void setFR(int fR) {
		FR = fR;
	}
	public String getLA() {
		return LA;
	}
	public void setLA(String lA) {
		LA = lA;
	}
	public int getBDV() {
		return BDV;
	}
	public void setBDV(int bDV) {
		BDV = bDV;
	}
	public IncConfig(int lTS, int dV, int bDV, double d, String lA) {
		super();
		LTS = lTS;
		DV = dV;
		BDV = bDV;
		FR = d;
		LA = lA;
	}
	public IncConfig() {
		super();
	}
	
	protected int LTS;// learning timespan
	protected int DV;// learning data volume
	protected int BDV;
	

	protected double FR;// learning fault rate
	protected String LA;// learning algorithm
	
}
