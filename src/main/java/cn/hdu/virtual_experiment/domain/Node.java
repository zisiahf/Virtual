package cn.hdu.virtual_experiment.domain;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class Node implements Serializable{
	private String node_id;
	
	private String node_name;
	//节点状态  0表示该节点未使用  1表示已使用
	private boolean isUsed = false;
	
	private String num;
	
	private String classification; //1 属于独享节点 2属于共享节点 3属于共享节点
	
	private Integer peopleNum = 0;
	
	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	
}
