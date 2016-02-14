package edu.ncepu.ferriad;
public class Inc_Config {
	
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
	public int getFR() {
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
	public Inc_Config(int lTS, int dV, int fR, String lA) {
		super();
		LTS = lTS;
		DV = dV;
		FR = fR;
		LA = lA;
	}
	public Inc_Config() {
		super();
	}
	
	protected int LTS;// learning timespan
	protected int DV;// learning data volume
	protected int FR;// learning fault rate
	protected String LA;// learning algorithm
	
}
