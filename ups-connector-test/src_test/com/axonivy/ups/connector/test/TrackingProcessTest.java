package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.ups.connector.TrackingRequestData;
import com.ups.wwwcie.client.TrackApiResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;

@IvyProcessTest(enableWebServer = true)
public class TrackingProcessTest extends BaseSetup {

  private static final BpmProcess testee = BpmProcess.path("Tracking");

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (TrackService API)";
  }

  @Test
  void getTracking(BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = testee.elementName("tracking(TrackingRequestData)");
    TrackingRequestData requestData = new TrackingRequestData();
    requestData.setInquiryNumber("1Z615V90DK63764633");
    requestData.setLocale("de_DE");
    requestData.setReturnSignature("false");
    requestData.setTransactionSrc("testing");
    requestData.setTransId("ciewssoatcnc0lRzHj9P2z");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute(requestData);
    var response = (TrackApiResponse) result.data().last().get("TrackApiResponse");
    assertThat(response.getTrackResponse().getShipment().get(0).getInquiryNumber()).isEqualTo("1Z615V90DK63764633");
  }

}