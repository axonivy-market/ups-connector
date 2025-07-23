package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.ups.wwwcie.api.client.XAVRequest;
import com.ups.wwwcie.api.client.XAVResponseWrapper;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;


public class AddressValidationProcessTest extends BaseSetup {
  
  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (Address Validation - Street Level)";
  }

	@TestTemplate
	void addressValidation(ExtensionContext context,BpmClient bpmClient) throws NoSuchFieldException {
		BpmElement startable = ADDRESS_VALIDATION.elementName("addressValidation(Integer,String,String,Integer,XAVRequest)");
		XAVRequest requestData = new XAVRequest();
		ExecutionResult result = bpmClient.start().subProcess(startable).execute(1, "v1", "1", 1, requestData);
		var response = (XAVResponseWrapper) result.data().last().get("xavResponseWrapper");
		if (response != null) {
			assertThat(response.getXaVResponse().getResponse().getResponseStatus().getCode()).isEqualTo("1");
			assertThat(response.getXaVResponse().getResponse().getResponseStatus().getDescription())
					.isEqualTo("Success");
		} else {
			assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }
}