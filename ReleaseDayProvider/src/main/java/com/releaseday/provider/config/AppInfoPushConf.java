package com.releaseday.provider.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * APP信息推送配置初始化类
 * 
 * @author hackless
 *
 */
@ConfigurationProperties(prefix = "appinfo")
public class AppInfoPushConf implements Serializable {

	private static final long serialVersionUID = 7892428764789181535L;

	private String appId;
	private String appKey;
	private String masterSecret;
	private String url;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
