package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;
import com.ups.wwwcie.api.client.PickupCancelResponse;
import com.ups.wwwcie.api.client.PickupCreationRequest;
import com.ups.wwwcie.api.client.PickupCreationRequestRequest;
import com.ups.wwwcie.api.client.PickupCreationResponse;
import com.ups.wwwcie.api.client.PickupPendingStatusResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;

/**
 * <p>
 * Detailed guidance on writing these kind of tests can be found in our <a href=
 * "https://developer.axonivy.com/doc/10.0/concepts/testing/process-testing.html">Process
 * Testing docs</a>
 * </p>
 */
public class PickupProcessTest extends BaseSetup {

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (Pickup)";
  }

  @TestTemplate
  void getPickUp(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PICKUP.elementName("pickupPendingStatus(String,String,String,String,String)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z", "testing",
        "2929602E9CP", "02");
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      var response = (PickupPendingStatusResponse) result.data().last().get("pickupPendingStatusResponse");
      assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("200");
      assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }

  @TestTemplate
  void cancelPickup(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PICKUP.elementName("pickupCancel(String,String,String,String,String)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z", "testing",
        "2929602E9CP", "02");
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      var response = (PickupCancelResponse) result.data().last().get("pickupCancelResponse");
      assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
      assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }

  @TestTemplate
  void createPickUp(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PICKUP.elementName("pickupCreation(String,String,String,PickupCreationRequest)");
    var request = new PickupCreationRequest();
    request.setRequest(new PickupCreationRequestRequest());
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z", "testing",
        request);
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      var response = (PickupCreationResponse) result.data().last().get("pickupCreationResponse");
      assertThat(response.getRateResult().getCurrencyCode()).isEqualTo("USD");
      assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }
}