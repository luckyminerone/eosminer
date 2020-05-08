package one.luckyminer.eosminer.model;
 
import party.loveit.eosforandroidlibrary.rpc.vo.ChainInfo;

public class BpNodeInfo {
	/**
	 * {"api_endpoint":"https://node1.zbeos.com","location":{"country":"Singapore","latitude":1.53,"longitude":103.74,"name":"ZBnode1"},
	 * "node_type":"full","p2p_endpoint":"node1.zbeos.com:9876","ssl_endpoint":"https://node1.zbeos.com"}
	 */
	public BpNodeInfo() {
		setNodeType("node_type");
	}

	public BpNodeInfo(String typeString) {
		setNodeType(typeString);
	}

	public ChainInfo getChainInfo() {
		return chainInfo;
	}

	public void setChainInfo(ChainInfo chainInfo) {
		this.chainInfo = chainInfo;
	}

	public String getApiEndpoint() {
		return api_endpoint;
	}

	public void setApiEndpoint(String api_endpoint) {
		this.api_endpoint = api_endpoint;
	}

	public String getNodeType() {
		return node_type;
	}

	public void setNodeType(String node_type) {
		this.node_type = node_type;
	}

	public String getP2pEndpoint() {
		return p2p_endpoint;
	}

	public void setP2pEndpoint(String p2p_endpoint) {
		this.p2p_endpoint = p2p_endpoint;
	}

	public String getSslEndpoint() {
		return ssl_endpoint;
	}

	public void setSslEndpoint(String ssl_endpoint) {
		this.ssl_endpoint = ssl_endpoint;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	private String api_endpoint;
	private String node_type;
	private String p2p_endpoint;
	private String ssl_endpoint;
	private Location location;
	private ChainInfo chainInfo;

	public String toString() {
		return "{type:" + node_type + ",api_endpoint:" + api_endpoint + ",p2p_endpoint:" + p2p_endpoint
				+ ",ssl_endpoint:" + ssl_endpoint + ",location:" + location + "}";

	}
}
