package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;
import com.axonivy.ups.connector.TrackingRequestData;
import com.ups.wwwcie.client.TrackApiResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;

public class TrackingProcessTest extends BaseSetup {

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (TrackService API)";
  }

  @TestTemplate
  void getTracking(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = TRACKING.elementName("tracking(TrackingRequestData)");
    TrackingRequestData requestData = new TrackingRequestData();
    requestData.setInquiryNumber("1Z615V90DK63764633");
    requestData.setLocale("de_DE");
    requestData.setReturnSignature("false");
    requestData.setTransactionSrc("testing");
    requestData.setTransId("ciewssoatcnc0lRzHj9P2z");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute(requestData);
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      var response = (TrackApiResponse) result.data().last().get("trackApiResponse");
      assertThat(response.getTrackResponse().getShipment().get(0).getInquiryNumber()).isEqualTo("1Z615V90DK63764633");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }
}