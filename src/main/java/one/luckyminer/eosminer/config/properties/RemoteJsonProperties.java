package one.luckyminer.eosminer.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource(value = { "classpath:config/remote.properties" }, encoding = "utf-8")
@ConfigurationProperties(prefix = "remote", ignoreUnknownFields = true)
public class RemoteJsonProperties {

	public String httpUrl;

	public String bpJsonHttpUrl;

	public String getBpJsonHttpUrl() {
		return bpJsonHttpUrl;
	}

	public void setBpJsonHttpUrl(String bpJsonHttpUrl) {
		this.bpJsonHttpUrl = bpJsonHttpUrl;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	@Override
	public String toString() {
		return String.format("http url: %s bp json http url:%s", httpUrl, bpJsonHttpUrl);
	}
}
