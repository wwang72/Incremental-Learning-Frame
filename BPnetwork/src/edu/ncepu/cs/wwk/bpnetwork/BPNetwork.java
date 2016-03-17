package edu.ncepu.cs.wwk.bpnetwork;

import java.util.Random;
/**
 * <p>BPNetwork</p>
 * <p>Description: </p>
 * <p>train the sample data with SGD，
 * adjust the connect weight</p>
 * <p>Institute: North China Electric Power U</p>
 * @author Weikang Wang
 * @version 1.1
 */
public class BPNetwork extends Object {
	
    public BPNetwork(BPConfig cf) {
        R = new Random();
        this.epochs = cf.getEpochs();
        int inNum = cf.getInNum();
        int hideNum = cf.getHideNum();
        int outNum = cf.getOutNum();
        this.inNum = inNum;
        this.hideNum = hideNum;
        this.outNum = outNum;
        this.input = new double[inNum]; 
        this.hidden = new double[hideNum]; 
        this.output = new double[outNum]; 
        this.o1 = new double[hideNum];
        this.o2 = new double[outNum];
        this. w_ih = new double[inNum][hideNum]; 
        this.w_ho = new double[hideNum][outNum]; 
        this.b1 = new double[hideNum]; 
        this.b2 = new double[outNum];
        this.correct_ih = new double[hideNum];
        this.correct_ho = new double[outNum];
        this.yd = new double[outNum];

        for (int i = 0; i < inNum; i++) {
            for (int j = 0; j < hideNum; j++) {
                w_ih[i][j] = R.nextDouble();
            }
        }
        for (int i = 0; i < hideNum; i++) {
            for (int j = 0; j < outNum; j++) {
                w_ho[i][j] = R.nextDouble();
            }
        }
        rate_w = cf.getRate_w(); 
        rate_w1 = cf.getRate_w1(); 
        rate_b1 = cf.getRate_b1(); 
        rate_b2 = cf.getRate_b2(); 
        e = cf.getE();
    }
    /*double p[][];
    double t[][];
    int samplenum;
    double psim[];
    public void setTrainDate(double p[][], double t[][]){
        this.p=p;
        this.t=t;
        samplenum=p.length;
    }
    public void setSimDate(double psim[]){
        this.psim=psim;
    }*/
    /**********************************/
    /*****BP神经控制器算法训练函数*****/
    public void train(double p[][], double t[][],int samplenum) {
    	for(int epoch=0;epoch<epochs;epoch++){
        e = 0.0;
        //归一化
        double pmax = 0.0;
        double tmax = 0.0;
        for (int isamp = 0; isamp < samplenum; isamp++) {
            for (int i = 0; i < inNum; i++) {
                if (Math.abs(p[isamp][i]) > pmax) {
                    pmax = Math.abs(p[isamp][i]);
                }
            }
            for (int j = 0; j < outNum; j++) {
                if (Math.abs(t[isamp][j]) > tmax) {
                	tmax = Math.abs(t[isamp][j]);
                }
            }
        } //end for isamp
        in_rate = pmax;
        out_rate = tmax;

        for (int isamp = 0; isamp < samplenum; isamp++) { //循环训练一次样本
            for (int i = 0; i < inNum; i++) {
                input[i] = p[isamp][i] / in_rate;
            }
            for (int i = 0; i < outNum; i++) {
                yd[i] = t[isamp][i] / out_rate;
            }
        /*this.input=p[isamp];
        this.yd=t[isamp];*/
            //构造每个样本的输入和输出标准

            for (int j = 0; j < hideNum; j++) {
                o1[j] = 0.0;
                for (int i = 0; i < inNum; i++) {
                    o1[j] = o1[j] + w_ih[i][j] * input[i];
                }
                hidden[j] = 1.0 / (1.0 + Math.exp( -o1[j] - b1[j])); //
            }

            for (int k = 0; k < outNum; k++) {
                o2[k] = 0.0;
                for (int j = 0; j < hideNum; j++) {
                    o2[k] = o2[k] + w_ho[j][k] * hidden[j];
                }
                output[k] = 1.0 / (1 + Math.exp( -o2[k] - b2[k])); //
                //output[k]=o2[k]-b2[k];
                e += Math.abs(yd[k] - output[k]) * Math.abs(yd[k] - output[k]); //计算均方差
            }

            //System.out.println("ok1");
            //update hidden layer weights
            //fixed problems
            for (int k = 0; k < outNum; k++) {
                correct_ho[k] = output[k] * (yd[k] - output[k]) * output[k] * (1. - output[k]);
                //correct_ho[k]=(yd[k]-output[k]);
                //e+=Math.abs(yd[k]-output[k])*Math.abs(yd[k]-output[k]);//计算均方差
                for (int j = 0; j < hideNum; j++) {
                    w_ho[j][k] = w_ho[j][k] + rate_w1 * correct_ho[k] * hidden[j];
                }
            }
            //update input layer weights
            for (int j = 0; j < hideNum; j++) {
                correct_ih[j] = 0.0;
                for (int k = 0; k < outNum; k++) {
                    correct_ih[j] = correct_ih[j] + correct_ho[k] / output[k] * w_ho[j][k];
                }
                correct_ih[j] = correct_ih[j] * hidden[j] * (1. - hidden[j]);
                for (int i = 0; i < inNum; i++) {
                    w_ih[i][j] = w_ih[i][j] + rate_w * correct_ih[j] * input[i];
                }
            }

            for (int k = 0; k < outNum; k++) {
                b2[k] = b2[k] + rate_b2 * correct_ho[k];
            }
            for (int j = 0; j < hideNum; j++) {
                b1[j] = b1[j] + rate_b1 * correct_ih[j];
            }
        } //end isamp样本循环
        e = Math.sqrt(e);
    	}
    } //end train

    /***************************************/
    /*****BP神经控制器算法模拟计算函数*******/
    public double[] sim(double psim[]) {
        for (int i = 0; i < inNum; i++) {
            input[i] = psim[i] / in_rate;
        }
        for (int j = 0; j < hideNum; j++) {
            o1[j] = 0.0;
            for (int i = 0; i < inNum; i++) {
                o1[j] = o1[j] + w_ih[i][j] * input[i];
            }
            hidden[j] = 1.0 / (1. + Math.exp( -o1[j] - b1[j]));
        }
        for (int k = 0; k < outNum; k++) {
            o2[k] = 0.0;
            for (int j = 0; j < hideNum; j++) {
                o2[k] = o2[k] + w_ho[j][k] * hidden[j];
            }
            output[k] = 1.0 / (1.0 + Math.exp( -o2[k] - b2[k]));
            //output[k]=o2[k]-b2[k];
            output[k] = out_rate * output[k];
        }
        return output;
    } //end sim
    private double input[]; //输入向量
	private double hidden[]; //隐含接点状态值
	private double output[]; //输出接点状态值
	private double o1[];//暂存第一层输出值
	private double o2[];//暂存第二层输出值
	double w_ih[][]; //隐含接点权值
	double w_ho[][]; //输出接点权值
	private double b1[]; //隐含接点阀值
    private double b2[]; //输出接点阀值
    private double correct_ih[];//输入层的隐含层的调整值
    private double correct_ho[];//隐含层和输出层的调整值
    private double yd[];
    double rate_w; //权值学习率（输入层-隐含层)
    double rate_w1; //权值学习率 (隐含层-输出层)
    double rate_b1; //隐含层阀值学习率
    double rate_b2; //输出层阀值学习率
    double e;
    double in_rate; //输入归一化比例系数
    double out_rate;//输出归一化比例系数
    int epochs;//迭代期数
    int inNum; //输入接点数
	int hideNum; //隐含接点数
	int outNum; //输出接点数
    Random R;
} //end bp class

