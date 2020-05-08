package one.luckyminer.eosminer.service;

import one.luckyminer.eosminer.model.NodeInfo;
import party.loveit.eosforandroidlibrary.Rpc;
import party.loveit.eosforandroidlibrary.rpc.vo.ChainInfo;

public class EosBlockServiceThread extends Thread {

	private NodeInfo nodeInfo;

	private ChainInfo chainInfo;

	public EosBlockServiceThread(NodeInfo nodeInfo) { 
		this.nodeInfo = nodeInfo;

	}

	public void run() {
		Rpc rpc = new Rpc(nodeInfo.getUrl());
		this.chainInfo = rpc.getChainInfo();
	}
	
	public ChainInfo getResult() {
		return chainInfo;
	}

}
