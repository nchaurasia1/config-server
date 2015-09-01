package com.taggstar.configapi.util;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.taggstar.configapi.service.APIResponse;

public class ResponseBuilderUtil {

	public static APIResponse getSuccessResponse(String moduleRunId) {

		return new APIResponse().withStatusCode(HttpServletResponse.SC_OK)
				.withModuleRunId(moduleRunId).withSuccess(true)
				.withResponseBody("{ \"response\": \"Request successful\"}")
				.withLastModified(new Date());

	}

	public static APIResponse getFailureResponse(String moduleRunId,
			String errorMessage) {
		return new APIResponse().withStatusCode(HttpServletResponse.SC_OK)
				.withModuleRunId(moduleRunId).withSuccess(false)
				.withResponseBody("{ \"response\": \"Request failed\"}")
				.withLastModified(new Date());
	}
}
