package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;
import com.ups.wwwcie.api.client.QuantumViewRequest;
import com.ups.wwwcie.api.client.QuantumViewResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;

public class QuantumViewTest extends BaseSetup {

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (Quantum View)";
  }

  @TestTemplate
  void quantumViewTest(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = QUATUM_VIEW.elementName("quatumView(String,QuantumViewRequest)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", new QuantumViewRequest());
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      var response = (QuantumViewResponse) result.data().last().get("quantumViewResponse");
      assertThat(response.getBookmark()).isEqualTo("Disqualified");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }
}