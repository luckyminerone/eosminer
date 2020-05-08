package one.luckyminer.eosminer.model;

import party.loveit.eosforandroidlibrary.rpc.vo.ChainInfo;

public class BpNodeChainInfo {
	// {"country":"Singapore","latitude":1.53,"longitude":103.74,"name":"ZBnode1"}
	private EosBpInfo eosBpInfo;
	public EosBpInfo getEosBpInfo() {
		return eosBpInfo;
	}

	public void setEosBpInfo(EosBpInfo eosBpInfo) {
		this.eosBpInfo = eosBpInfo;
	}

	public ChainInfo getChainInfo() {
		return chainInfo;
	}

	public void setChainInfo(ChainInfo chainInfo) {
		this.chainInfo = chainInfo;
	}

	private ChainInfo chainInfo;

	public String toString() {
		return "{eosBpInfo:" + eosBpInfo + ",chainInfo:" + chainInfo + "}";

	}
}
