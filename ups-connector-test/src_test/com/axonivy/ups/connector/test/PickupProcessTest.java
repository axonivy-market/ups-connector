package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ups.wwwcie.api.client.PickupCancelResponse;

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
@IvyProcessTest(enableWebServer = true)
public class PickupProcessTest {

	private static final BpmProcess testee = BpmProcess.path("Pickup");

	@BeforeEach
	void beforeEach(AppFixture fixture) {
		// Disable OAuth feature for mock rest service
		fixture.config("RestClients.ups (Pickup).Features", "ch.ivyteam.ivy.rest.client.mapper.JsonFeature");
		fixture.var("upsConnector.Url", "{ivy.app.baseurl}/api/upsMock");
	}

	@Test
	void cancelPickup(BpmClient bpmClient) throws NoSuchFieldException {
		BpmElement startable = testee.elementName("pickupCancel(String,String,String,String,String)");
		ExecutionResult result = bpmClient.start().subProcess(startable).execute("1", "ciewssoatcnc0lRzHj9P2z",
				"testing", "2929602E9CP", "02");
		var response = (PickupCancelResponse) result.data().last().get("PickupCancelResponse");
		assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
		assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
	}
}