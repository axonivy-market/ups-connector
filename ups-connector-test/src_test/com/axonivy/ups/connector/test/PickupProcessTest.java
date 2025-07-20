package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import com.ups.wwwcie.api.client.PickupCancelResponse;
import com.ups.wwwcie.api.client.PickupCreationRequest;
import com.ups.wwwcie.api.client.PickupCreationResponse;
import com.ups.wwwcie.api.client.PickupGetServiceCenterFacilitiesRequest;
import com.ups.wwwcie.api.client.PickupGetServiceCenterFacilitiesResponse;
import com.ups.wwwcie.api.client.PickupPendingStatusResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;

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
  void getPickUp(BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PICKUP.elementName("pickupPendingStatus(String,String,String,String,String)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z", "testing",
        "2929602E9CP", "02");
    var response = (PickupPendingStatusResponse) result.data().last().get("pickupPendingStatusResponse");
    assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("200");
    assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
  }

  @TestTemplate
  void cancelPickup(BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PICKUP.elementName("pickupCancel(String,String,String,String,String)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z", "testing",
        "2929602E9CP", "02");
    var response = (PickupCancelResponse) result.data().last().get("pickupCancelResponse");
    assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
    assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
  }
  
  @TestTemplate
  void createPickUp(BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PICKUP.elementName("pickupCreation(String,String,String,PickupCreationRequest)");    
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z", "testing",
        new PickupCreationRequest().ratePickupIndicator("test"));
    var response = (PickupCreationResponse) result.data().last().get("pickupCreationResponse");
    assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
    assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
  }

  @TestTemplate
  void createServiceCenter(BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PICKUP.elementName("pickupGetServiceCenterFacilities(String,String,String,PickupGetServiceCenterFacilitiesRequest)");    
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z", "testing",
        new PickupGetServiceCenterFacilitiesRequest());
    var response = (PickupGetServiceCenterFacilitiesResponse) result.data().last().get("pickupGetServiceCenterFacilitiesResponse");
    assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
    assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
  }
}