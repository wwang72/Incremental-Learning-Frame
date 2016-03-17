package edu.ncepu.cs.wwk.bpnetwork;

import java.util.Random;

public class BPConfig {
	
	public int getInNum() {
		return inNum;
	}
	/**
	 * <p>public BPConfig(int inNum, int hideNum, int outNum)</p>
	 * <p><b>inNum</b>: input layer node number</p>
	 * <p><b>hideNum</b>: hide layer node number</p>
	 * <p><b>inNum</b>: output layer node number</p>
	 * <p><b>ephochs</b>: output layer node number</p>*/
	public BPConfig(int inNum, int hideNum, int outNum, int epochs) {
		super();
		this.inNum = inNum;
		this.hideNum = hideNum;
		this.outNum = outNum;
		this.epochs = epochs;
		this.rate_w = 0.08; //权值学习率（输入层-隐含层)
		this.rate_w1 = 0.08; //权值学习率 (隐含层-输出层)
		this.rate_b1 = 0.05; //隐含层阀值学习率
		this.rate_b2 = 0.05; //输出层阀值学习率
		this.e = 0.00;
	}
	
	/**
	 * <p>public BPConfig(int inNum, int hideNum, int outNum)</p>
	 * <p><b>inNum</b>: input layer node number</p>
	 * <p><b>hideNum</b>: hide layer node number</p>
	 * <p><b>inNum</b>: output layer node number</p>*/
	public BPConfig(int inNum, int hideNum, int outNum) {
		super();
		this.inNum = inNum;
		this.hideNum = hideNum;
		this.outNum = outNum;
		this.epochs = 500;
		this.rate_w = 0.01; //权值学习率（输入层-隐含层)
		this.rate_w1 = 0.01; //权值学习率 (隐含层-输出层)
		this.rate_b1 = 0.02; //隐含层阀值学习率
		this.rate_b2 = 0.02; //输出层阀值学习率
		this.e = 0.00;
	}
	public void setInNum(int inNum) {
		this.inNum = inNum;
	}
	public int getHideNum() {
		return hideNum;
	}
	public void setHideNum(int hideNum) {
		this.hideNum = hideNum;
	}
	public int getOutNum() {
		return outNum;
	}
	public void setOutNum(int outNum) {
		this.outNum = outNum;
	}
	public Random getR() {
		return R;
	}
	public void setR(Random r) {
		R = r;
	}
	public int getEpochs() {
		return epochs;
	}
	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}
	public double getRate_w() {
		return rate_w;
	}
	public void setRate_w(double rate_w) {
		this.rate_w = rate_w;
	}
	public double getRate_w1() {
		return rate_w1;
	}
	public void setRate_w1(double rate_w1) {
		this.rate_w1 = rate_w1;
	}
	public double getRate_b1() {
		return rate_b1;
	}
	public void setRate_b1(double rate_b1) {
		this.rate_b1 = rate_b1;
	}
	public double getRate_b2() {
		return rate_b2;
	}
	public void setRate_b2(double rate_b2) {
		this.rate_b2 = rate_b2;
	}
	public double getE() {
		return e;
	}
	public void setE(double e) {
		this.e = e;
	}
	public double getDe() {
		return de;
	}
	public void setDe(double de) {
		this.de = de;
	}
	public double getIn_rate() {
		return in_rate;
	}
	public void setIn_rate(double in_rate) {
		this.in_rate = in_rate;
	}
	public double getOut_rate() {
		return out_rate;
	}
	public void setOut_rate(double out_rate) {
		this.out_rate = out_rate;
	}
	protected int inNum; //输入接点数
	protected int hideNum; //隐含接点数
	protected int outNum; //输出接点数
	protected Random R;
	protected int epochs;//迭代期数
	protected double rate_w; //权值学习率（输入层-隐含层)
	protected double rate_w1; //权值学习率 (隐含层-输出层)
	protected double rate_b1; //隐含层阀值学习率
	protected double rate_b2; //输出层阀值学习率
	protected double e;
	protected double de;
	protected double in_rate; //输入归一化比例系数
	protected double out_rate;//输出归一化比例系数
}
