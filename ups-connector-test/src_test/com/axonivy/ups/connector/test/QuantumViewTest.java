package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.ups.wwwcie.api.client.PreNotificationRequest;
import com.ups.wwwcie.api.client.PreNotificationResponse;
import com.ups.wwwcie.api.client.QuantumViewRequest;
import com.ups.wwwcie.api.client.QuantumViewResponse;
import com.ups.wwwcie.api.client.XAVRequest;
import com.ups.wwwcie.api.client.XAVRequestAddressKeyFormat;
import com.ups.wwwcie.api.client.XAVRequestRequest;
import com.ups.wwwcie.api.client.XAVResponseWrapper;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;


public class QuantumViewTest extends BaseSetup {
  
  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (Quantum View)";
  }

	@TestTemplate
	void preNotificationTest(ExtensionContext context,BpmClient bpmClient) throws NoSuchFieldException {
		BpmElement startable = QUATUM_VIEW.elementName("quatumView(String,QuantumViewRequest)");
		ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", new QuantumViewRequest());
		var response = (QuantumViewResponse) result.data().last().get("quantumViewResponse");
			assertThat(response.getResponse().getResponseStatusCode()).isEqualTo("1");
//			assertThat(response.getResponse().get().getDescription())
//					.isEqualTo("Success");

	}
}