package one.luckyminer.eosminer.runner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import one.luckyminer.eosminer.config.properties.RemoteJsonProperties;
import one.luckyminer.eosminer.dao.EosBpInfoDAO;
import one.luckyminer.eosminer.model.BpNodeChainInfo;
import one.luckyminer.eosminer.model.BpNodeInfo;
import one.luckyminer.eosminer.model.EosBpInfo;
import one.luckyminer.eosminer.model.NodeInfo;
import one.luckyminer.eosminer.service.EosBlockService;
import party.loveit.eosforandroidlibrary.rpc.vo.ChainInfo;

@Component
public class AppRunner implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

	private final EosBlockService eosBlockService;

	// @Resource(name = "threadPoolTaskExecutor")
	@Autowired
	private ThreadPoolTaskExecutor executor;

	public AppRunner(EosBlockService eosBlockService) {
		this.eosBlockService = eosBlockService;
	}

	@Autowired
	RemoteJsonProperties remoteJsonProperties;

	public List<NodeInfo> getNodeInfoList() {
		String httpUrl = remoteJsonProperties.getHttpUrl();
		System.out.println("httpUrl:" + httpUrl);
		List<NodeInfo> nodelist;
		try {
			nodelist = getNodeInfoList(httpUrl);
			return nodelist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<BpNodeInfo> getBpNodeInfoList() {
		String bphttpUrl = remoteJsonProperties.getBpJsonHttpUrl();
		System.out.println("httpUrl:" + bphttpUrl);
		try {
			return getBpNodeInfoList(bphttpUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<BpNodeChainInfo> getBpNodeChainInfoList() {
		List<BpNodeChainInfo> bplist = new ArrayList<>();
		EosBpInfoDAO dao = new EosBpInfoDAO();
		List<EosBpInfo> list = dao.queryEosBpInfoList();
		for (EosBpInfo eosBpInfo : list) {
			BpNodeChainInfo bpNodeChainInfo = new BpNodeChainInfo();
			bpNodeChainInfo.setEosBpInfo(eosBpInfo);
			bplist.add(bpNodeChainInfo);
		}
		return bplist;
	}

	private List<BpNodeInfo> getBpNodeInfoList(String urlString) throws Exception {
		List<BpNodeInfo> list = new ArrayList<>();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10000);// 设置超时
		requestFactory.setReadTimeout(60000);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		URI uri = new URI(urlString);// "https://validate.eosnation.io/eos/reports/endpoints.json"
		String result = "";
		for (int i = 0; i < 10; i++) {
			try {
				result = restTemplate.exchange(uri, HttpMethod.GET, null, String.class).getBody();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (result == "") {
			throw new Exception("read endpoints error");
		}

		JSONObject json = JSONObject.fromObject(result);
		JSONArray producers = (JSONArray) json.get("producers");
		for (int i = 0; i < producers.size(); i++) {
			JSONObject reportJsonObject = (JSONObject) producers.get(i);
			if (reportJsonObject != null) {
				JSONObject inputJsonObject = reportJsonObject.getJSONObject("input");
				if (inputJsonObject != null) {
					JSONArray nodesJsonArray = inputJsonObject.getJSONArray("nodes");
					System.out.println("nodesJsonArray:" + nodesJsonArray);
					if (nodesJsonArray != null) {
						for (int j = 0; j < nodesJsonArray.size(); j++) {
							JSONObject nodeObject = (JSONObject) nodesJsonArray.get(i);
							BpNodeInfo bpNodeInfo = (BpNodeInfo) JSONObject.toBean(nodeObject, BpNodeInfo.class);
							list.add(bpNodeInfo);
						}
					}

				}
			}

		}
		return list;
	}

	private List<NodeInfo> getNodeInfoList(String urlString) throws Exception {
		List<NodeInfo> list = new ArrayList<>();
		List<NodeInfo> tmplist = new ArrayList<>();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10000);// 设置超时
		requestFactory.setReadTimeout(10000);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		URI uri = new URI(urlString);// "https://validate.eosnation.io/eos/reports/endpoints.json"
		String result = "";
		for (int i = 0; i < 10; i++) {
			try {
				result = restTemplate.exchange(uri, HttpMethod.GET, null, String.class).getBody();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (result == "") {
			throw new Exception("read endpoints error");
		}
		JSONObject json = JSONObject.fromObject(result);
		// JSONObject metaJsonObject = json.getJSONObject("meta");
		JSONObject reportJsonObject = json.getJSONObject("report");

		JSONArray api_httpJsonObject = (JSONArray) reportJsonObject.get("api_http");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "api_http");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		api_httpJsonObject = (JSONArray) reportJsonObject.get("api_https");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "api_https");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		api_httpJsonObject = (JSONArray) reportJsonObject.get("api_https2");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "api_https2");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		api_httpJsonObject = (JSONArray) reportJsonObject.get("history_traditional_http");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "history_traditional_http");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		api_httpJsonObject = (JSONArray) reportJsonObject.get("history_traditional_https");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "history_traditional_https");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		api_httpJsonObject = (JSONArray) reportJsonObject.get("hyperion_http");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "hyperion_http");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		api_httpJsonObject = (JSONArray) reportJsonObject.get("hyperion_https");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "hyperion_https");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		api_httpJsonObject = (JSONArray) reportJsonObject.get("p2p");
		tmplist = getNodeInfosByJsonArray(api_httpJsonObject, "p2p");
		if (tmplist != null) {
			list.addAll(tmplist);
		}

		return list;
	}

	/**
	 * 
	 * @param JSONArray
	 * @param type
	 * @return
	 * @throws URISyntaxException
	 */
	private List<NodeInfo> getNodeInfosByJsonArray(JSONArray JSONArray, String type) throws URISyntaxException {
		if (JSONArray == null || JSONArray.size() < 1) {
			return null;
		}
		List<NodeInfo> list = new ArrayList<>();
		int count = JSONArray.size();
		for (int i = 0; i < count; i++) {
			JSONArray idArray = (JSONArray) JSONArray.get(i);
			JSONObject info = (JSONObject) idArray.get(0);
			String html_name = info.getString("html_name");
			String eosAccountName = info.getString("name");
			String urlString = idArray.getString(1);
			String desString = idArray.getString(2);
			NodeInfo nodeInfo = new NodeInfo(type);
			nodeInfo.setName(html_name);
			nodeInfo.setEosAccountName(eosAccountName);
			nodeInfo.setDesception(desString);
			nodeInfo.setUrl(urlString);
			list.add(nodeInfo);
		}
		return list;
	}

	@Override
	public void run(String... args) throws Exception {
		// Start the clock
		// long start = System.currentTimeMillis();
		List<NodeInfo> nodeInfoList = this.getNodeInfoList();
		List<CompletableFuture<NodeInfo>> futures = new ArrayList<CompletableFuture<NodeInfo>>();
		System.out.println("run....nodeInfoList size:" + nodeInfoList.size());
		for (NodeInfo nodeInfo : nodeInfoList) {
			if (nodeInfo.getType() != "p2p") {
				CompletableFuture<NodeInfo> infoCompletableFuture = eosBlockService.getChainInfo(nodeInfo);
				futures.add(infoCompletableFuture);
			}
		}

		List<BpNodeInfo> bpNodeInfoList = this.getBpNodeInfoList();
		List<CompletableFuture<BpNodeInfo>> bpFutures = new ArrayList<CompletableFuture<BpNodeInfo>>();
		System.out.println("run....bp nodeInfoList size:" + nodeInfoList.size());
		for (BpNodeInfo nodeInfo : bpNodeInfoList) {
			if (nodeInfo.getNodeType() == "full") {
				CompletableFuture<BpNodeInfo> infoCompletableFuture = eosBlockService.getBpChainInfo(nodeInfo);
				bpFutures.add(infoCompletableFuture);
			}
		}

		List<BpNodeChainInfo> bpNodeChainInfos = this.getBpNodeChainInfoList();
		List<CompletableFuture<BpNodeChainInfo>> bpNodeFutures = new ArrayList<CompletableFuture<BpNodeChainInfo>>();
		System.out.println("run....bp nodeInfoList size:" + bpNodeChainInfos.size());
		for (BpNodeChainInfo nodeInfo : bpNodeChainInfos) {
			if (nodeInfo.getEosBpInfo().getUrl() != null || nodeInfo.getEosBpInfo().getUrl() != "") {
				CompletableFuture<BpNodeChainInfo> infoCompletableFuture = eosBlockService.getBpChainInfo(nodeInfo);
				bpNodeFutures.add(infoCompletableFuture);
			}
		}

		System.out.println("futures:" + futures.size());
		while (true) {
			for (int i = 0; i < futures.size(); i++) {
				CompletableFuture<NodeInfo> completableFuture = futures.get(i);
				if (completableFuture.isDone()) {
					NodeInfo nodeInfo = null;
					nodeInfo = completableFuture.get();
					if (nodeInfo != null) {
						ChainInfo chainInfo = nodeInfo.getChainInfo();
						if (chainInfo != null) {
							System.out.println("head block num:" + chainInfo.getHeadBlockNum() + " hash:"
									+ chainInfo.getHeadBlockId());
						}
					}
					CompletableFuture<NodeInfo> infoCompletableFuture = eosBlockService.getChainInfo(nodeInfo);
					futures.set(i, infoCompletableFuture);
				}
			}
			for (int i = 0; i < bpFutures.size(); i++) {
				CompletableFuture<BpNodeInfo> completableFuture = bpFutures.get(i);
				if (completableFuture.isDone()) {
					BpNodeInfo nodeInfo = null;
					nodeInfo = completableFuture.get();
					if (nodeInfo != null) {
						ChainInfo chainInfo = nodeInfo.getChainInfo();
						if (chainInfo != null) {
							System.out.println("head block num:" + chainInfo.getHeadBlockNum() + " hash:"
									+ chainInfo.getHeadBlockId());
						}
					}
					CompletableFuture<BpNodeInfo> infoCompletableFuture = eosBlockService.getBpChainInfo(nodeInfo);
					bpFutures.set(i, infoCompletableFuture);
				}
			}

			for (int i = 0; i < bpNodeFutures.size(); i++) {
				CompletableFuture<BpNodeChainInfo> completableFuture = bpNodeFutures.get(i);
				if (completableFuture.isDone()) {
					BpNodeChainInfo nodeInfo = null;
					nodeInfo = completableFuture.get();
					if (nodeInfo != null) {
						ChainInfo chainInfo = nodeInfo.getChainInfo();
						if (chainInfo != null) {
							System.out.println("head block num:" + chainInfo.getHeadBlockNum() + " hash:"
									+ chainInfo.getHeadBlockId());
						}
					}
					CompletableFuture<BpNodeChainInfo> infoCompletableFuture = eosBlockService.getBpChainInfo(nodeInfo);
					bpNodeFutures.set(i, infoCompletableFuture);
				}
			}
		}
	}
}
