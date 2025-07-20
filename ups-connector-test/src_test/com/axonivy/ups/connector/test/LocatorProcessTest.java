package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.ups.wwwcie.api.client.LOCATORResponseWrapper;
import com.ups.wwwcie.api.client.LocatorRequest;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;

public class LocatorProcessTest extends BaseSetup {

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (Locator)";
  }

	@TestTemplate
	void testGetLocator(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
		BpmElement startable = LOCATOR.elementName("locator(String,String,String,String,String,LocatorRequest)");
		LocatorRequest request = new LocatorRequest();
		ExecutionResult result = bpmClient.start().subProcess(startable).execute("VN", "v1", "VN", "v1", "v1", request);
		var response = (LOCATORResponseWrapper) result.data().last().get("locatorResponseWrapper");
		if (response != null) {
			assertThat(response.getLocatorResponse().getResponse().getResponseStatusCode()).isEqualTo("1");
			assertThat(response.getLocatorResponse().getResponse().getResponseStatusDescription()).isEqualTo("Success");
		} else {
			assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
		}
	}
}