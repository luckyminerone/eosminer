package one.luckyminer.eosminer.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import one.luckyminer.eosminer.model.BpNodeChainInfo;
import one.luckyminer.eosminer.model.BpNodeInfo;
import one.luckyminer.eosminer.model.EosBpInfo;
import one.luckyminer.eosminer.model.NodeInfo;
import party.loveit.eosforandroidlibrary.Rpc;
import party.loveit.eosforandroidlibrary.rpc.vo.ChainInfo;

@Service
public class EosBlockService {
	private static final Logger logger = LoggerFactory.getLogger(EosBlockService.class);

	private final RestTemplate restTemplate;

	public EosBlockService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	// 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行(并指定线程池的名字)
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<NodeInfo> getChainInfo(NodeInfo nodeInfo) throws InterruptedException {
		logger.info("Looking up " + nodeInfo.getUrl());
		Rpc rpc = new Rpc(nodeInfo.getUrl());
		ChainInfo chaininfo = null;
		try {
			chaininfo = rpc.getChainInfo();
			nodeInfo.setChainInfo(chaininfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return CompletableFuture.completedFuture(nodeInfo);
	}

	// 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行(并指定线程池的名字)
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<BpNodeInfo> getBpChainInfo(BpNodeInfo nodeInfo) throws InterruptedException {
		logger.info("bp Looking up " + nodeInfo.getApiEndpoint());
		String urlString = nodeInfo.getApiEndpoint();
		if (urlString == null) {
			urlString = nodeInfo.getSslEndpoint();
		}
		if (urlString != null) {
			Rpc rpc = new Rpc(nodeInfo.getApiEndpoint());
			ChainInfo chaininfo = null;
			try {
				chaininfo = rpc.getChainInfo();
				nodeInfo.setChainInfo(chaininfo);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return CompletableFuture.completedFuture(nodeInfo);
	}

	// 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行(并指定线程池的名字)
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<BpNodeChainInfo> getBpChainInfo(BpNodeChainInfo nodeInfo) throws InterruptedException {
		logger.info("bp Looking up " + nodeInfo.getEosBpInfo().getUrl());
		String urlString = nodeInfo.getEosBpInfo().getUrl();
		if (urlString != null) {
			Rpc rpc = new Rpc(urlString);
			ChainInfo chaininfo = null;
			try {
				chaininfo = rpc.getChainInfo();
				nodeInfo.setChainInfo(chaininfo);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return CompletableFuture.completedFuture(nodeInfo);
	}
}
