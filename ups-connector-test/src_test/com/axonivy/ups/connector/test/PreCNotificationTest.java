package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;
import com.ups.wwwcie.api.client.PreNotificationRequest;
import com.ups.wwwcie.api.client.PreNotificationResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;

public class PreCNotificationTest extends BaseSetup {

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (PreNotification)";
  }

  @TestTemplate
  void preNotificationTest(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PRE_NOTIFICATION.elementName("preNotification(String,String,String,PreNotificationRequest)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "v1", "1",
        new PreNotificationRequest());
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      var response = (PreNotificationResponse) result.data().last().get("preNotificationResponse");
      assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
      assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }
}