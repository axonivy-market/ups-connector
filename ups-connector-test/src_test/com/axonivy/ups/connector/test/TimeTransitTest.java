package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;
import com.ups.wwwcie.api.client.TimeInTransitRequest;
import com.ups.wwwcie.api.client.TimeInTransitResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;

public class TimeTransitTest extends BaseSetup {

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (Time In Transit)";
  }

  @TestTemplate
  void createTransitTimeTest(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = TIME_IN_TRANSIT.elementName("call(String,String,String,TimeInTransitRequest)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("test", "test", "test",
        new TimeInTransitRequest());
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      System.out.print(result.data().last());
      var response = (TimeInTransitResponse) result.data().last().get("timeInTransitResponse");
      assertThat(response.getValidationList().isDestinationAmbiguous()).isEqualTo(true);
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }
}