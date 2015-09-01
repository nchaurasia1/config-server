package com.taggstar.configapi.dto;

import com.taggstar.configapi.model.Configuration;
import com.taggstar.configapi.model.RuleConfiguration;
import com.taggstar.configapi.model.SiteConfiguration;

/**
 * Created by andy on 01/07/2015.
 */
public class ConfigRequestDTO {

	private String key;
	private String type; // config type 'Site' or "rule"
	private int version;
	private SiteConfiguration siteConfig;
	private RuleConfiguration ruleConfig;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public SiteConfiguration getSiteConfig() {
		return siteConfig;
	}
	public void setSiteConfig(SiteConfiguration siteConfig) {
		this.siteConfig = siteConfig;
	}
	public RuleConfiguration getRuleConfig() {
		return ruleConfig;
	}
	public void setRuleConfig(RuleConfiguration ruleConfig) {
		this.ruleConfig = ruleConfig;
	}
	@Override
	public String toString() {
		return "ConfigRequestDTO [key=" + key + ", type=" + type + ", version="
				+ version + ", siteConfig=" + siteConfig + ", ruleConfig="
				+ ruleConfig + "]";
	}
	
}
