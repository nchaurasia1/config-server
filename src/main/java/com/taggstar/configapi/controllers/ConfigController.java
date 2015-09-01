package com.taggstar.configapi.controllers;

import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.taggstar.configapi.dto.ConfigRequestDTO;
import com.taggstar.configapi.service.APIResponse;
import com.taggstar.configapi.service.AppConfig;
import com.taggstar.configapi.util.ResponseBuilderUtil;

/**
 * Conversion API methods.
 */
public class ConfigController {

    private static final Log log = LogFactory.getLog(ConfigController.class);
    private AppConfig appConfig;
    private AmazonSQSAsyncClient sqsClient;

    public ConfigController(AppConfig appConfig,AmazonSQSAsyncClient sqsClient) {
    	this.appConfig = appConfig;
    	this.sqsClient =  sqsClient;
    }

    public APIResponse createConfig(String moduleRunId, ConfigRequestDTO configRequestDTO) {
    	log.info("Creating new Configuration object with moduleRunId [ "+ moduleRunId+ " ] and Request data ["+ configRequestDTO +"]");
        return ResponseBuilderUtil.getSuccessResponse(moduleRunId);

    }
    
    public APIResponse updateConfig(String moduleRunId,ConfigRequestDTO configRequestDTO) {
    	log.info("updating Configuration object with moduleRunId [ "+ moduleRunId+ " ] and Request data ["+ configRequestDTO +"]");
    	return ResponseBuilderUtil.getSuccessResponse(moduleRunId);

    }
    
    public APIResponse deleteConfig(String moduleRunId, ConfigRequestDTO configRequestDTO) {
    	log.info("deleting Configuration object with moduleRunId [ "+ moduleRunId+ " ] and Request data ["+ configRequestDTO +"]");
    	return ResponseBuilderUtil.getSuccessResponse(moduleRunId);

    }
    
    public APIResponse getConfig(String moduleRunId, String configType, int version) {
    	log.info("Fetching Configuration object with moduleRunId [ "+ moduleRunId+ " ] for configType ["+ configType +"] and version:[ " +version+ " ]");
    	return ResponseBuilderUtil.getSuccessResponse(moduleRunId);

    }

}
