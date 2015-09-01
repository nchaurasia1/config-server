package com.taggstar.configapi.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.handler.AbstractHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taggstar.configapi.controllers.ConfigController;
import com.taggstar.configapi.dto.ConfigRequestDTO;

/**
 * Handle HTTP requests and delegate to domain logic.
 */
public class HttpHandler extends AbstractHandler {

	private ConfigController configController;
	private Charset charset = Charset.forName("UTF-8");

	private static final Log log = LogFactory.getLog(HttpHandler.class);

	/**
	 * Construct with controller classes to delegate valid requests to.
	 *
	 * @throws Exception
	 *             if setup fails.
	 */
	public HttpHandler(ConfigController configController)
			throws Exception {
		this.configController = configController;
	}

	public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,int dispatch)
			throws IOException, ServletException {

		String uri = httpServletRequest.getRequestURI();
		String moduleRunId = UUID.randomUUID().toString();
		String method = httpServletRequest.getMethod();
		String[] parts = uri.split("/");
		String requestType = parts[parts.length-1];
		APIResponse response =  null;
	    
		
		if (uri.matches("^/api/v1/site/[A-Za-z0-9\\-\\.]+/config.*$")) {
			if (method.equals("POST")) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					ConfigRequestDTO configRequestDTO = objectMapper.readValue(getRequestBody(httpServletRequest),ConfigRequestDTO.class);
					if (requestType.equals("create")) {
						response = configController.createConfig(moduleRunId,configRequestDTO);
					} else if (requestType.equals("update")) {
						response = configController.updateConfig(moduleRunId,configRequestDTO);
					} else if (requestType.equals("delete")) {
						response = configController.deleteConfig(moduleRunId,configRequestDTO);
					}
				} catch (Exception e) {

					log.error("exception request-id=[" + moduleRunId + "]", e);
					writePlainTextResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,httpServletResponse,"Error on exception " + e.toString(), moduleRunId);
				}
				writeJSONResponse(httpServletResponse, response);
			} else if (method.equals("GET")) {
				String configType = httpServletRequest.getParameter("type");
				String versionStr = httpServletRequest.getParameter("version");
				int version;
				try {
					version = Integer.parseInt(versionStr);
				} catch (NumberFormatException e) { // for invalid version
													// return latest version
					version = -1;
				}
				response = configController.getConfig(moduleRunId, configType,version);
				writeJSONResponse(httpServletResponse, response);
			}
			writePlainTextResponse(HttpServletResponse.SC_NOT_FOUND,httpServletResponse, "Not found", moduleRunId);
		} else {

			writePlainTextResponse(HttpServletResponse.SC_NOT_FOUND,httpServletResponse, "Not found", moduleRunId);
		}
	}

	protected void writeJSONResponse(HttpServletResponse httpServletResponse, APIResponse endpointResponse)
			throws IOException {

		httpServletResponse.setContentType("application/json");
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setStatus(endpointResponse.getHttpStatusCode());
		httpServletResponse.setHeader("x-request-id", endpointResponse.getModuleRunId());

		if (endpointResponse.getLastModifiedHeader() != null) {
			httpServletResponse.setHeader("Last-Modified", endpointResponse.getLastModifiedHeader());
		}

		// responseBody can be null in some cases (.e.g 204 no content, 404 not
		// found)
		if (endpointResponse.getResponseBody() != null) {
			String responseBody = endpointResponse.getResponseBody();
			httpServletResponse.setContentLength(responseBody.length());
			httpServletResponse.getWriter().write(responseBody);
			httpServletResponse.getWriter().flush();
		}

		httpServletResponse.flushBuffer();

	}

	protected void writePlainTextResponse(int statusCode, HttpServletResponse httpServletResponse, String text,
			String moduleRunId) throws IOException {

		httpServletResponse.setStatus(statusCode);
		httpServletResponse.setContentType("text/plain");
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setHeader("x-request-id", moduleRunId);
		httpServletResponse.setContentLength(text.length());
		httpServletResponse.getWriter().write(text);
		httpServletResponse.getWriter().flush();
		httpServletResponse.flushBuffer();

	}

	protected String getRequestBody(HttpServletRequest request) throws Exception {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		InputStream is = request.getInputStream();
		int i = 0;
		byte[] buff = new byte[1024];
		while ((i = is.read(buff)) > 0) {
			byteArrayOutputStream.write(buff, 0, i);
		}
		return byteArrayOutputStream.toString("UTF-8");

	}

	
	

	
}
