package one.luckyminer.eosminer.model;

public class EosBpInfo {
	private String url;
	private int type;

	private String name;
	private int status;
	private int ctime;
	private int ext1;
	private String extstr;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCtime() {
		return ctime;
	}

	public void setCtime(int ctime) {
		this.ctime = ctime;
	}

	public int getExt1() {
		return ext1;
	}

	public void setExt1(int ext1) {
		this.ext1 = ext1;
	}

	public String getExtstr() {
		return extstr;
	}

	public void setExtstr(String extstr) {
		this.extstr = extstr;
	}

	public String toString() {
		return "url" + url + "	type:" + type + "	status:" + status + "	ctime:" + ctime + "	ext1:" + ext1
				+ "  extstr:" + extstr;
	}
}