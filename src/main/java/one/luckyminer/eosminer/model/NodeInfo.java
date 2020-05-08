package one.luckyminer.eosminer.model;

import party.loveit.eosforandroidlibrary.rpc.vo.ChainInfo;

public class NodeInfo {

	public NodeInfo() {
		setType("http");
	}

	public NodeInfo(String typeString) {
		setType(typeString);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesception() {
		return desception;
	}

	public void setDesception(String desception) {
		this.desception = desception;
	}

	public String getEosAccountName() {
		return eosAccountName;
	}

	public void setEosAccountName(String eosAccountName) {
		this.eosAccountName = eosAccountName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

	public ChainInfo getChainInfo() {
		return chainInfo;
	}

	public void setChainInfo(ChainInfo chainInfo) {
		this.chainInfo = chainInfo;
	}

	private String type;
	private String name;
	private String desception;
	private String eosAccountName;
	private String url;
	private ChainInfo chainInfo;

	public String toString() {
		return "{type:" + type + ",name:" + name + ",desc:" + desception + ",account_name:" + eosAccountName + ",url:"
				+ url + "}";

	}
}
