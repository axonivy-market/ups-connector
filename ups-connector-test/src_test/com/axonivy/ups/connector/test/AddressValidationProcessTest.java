package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ups.wwwcie.api.client.XAVRequest;
import com.ups.wwwcie.api.client.XAVRequestAddressKeyFormat;
import com.ups.wwwcie.api.client.XAVRequestRequest;
import com.ups.wwwcie.api.client.XAVResponseWrapper;

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
public class AddressValidationProcessTest {

	private static final BpmProcess testee = BpmProcess.path("AddressValidation");

	@BeforeEach
	void beforeEach(AppFixture fixture) {
		// Disable OAuth feature for mock rest service
		fixture.config("RestClients.ups (Address Validation - Street Level).Features",
				"ch.ivyteam.ivy.rest.client.mapper.JsonFeature");
		fixture.var("upsConnector.Url", "{ivy.app.baseurl}/api/upsMock");
	}

	@Test
	void addressValidation(BpmClient bpmClient) throws NoSuchFieldException {
		BpmElement startable = testee.elementName("addressValidation(Integer,String,String,Integer,XAVRequest)");
		XAVRequest requestData = new XAVRequest();
		requestData.setRequest(new XAVRequestRequest());
		requestData.getRequest().setRequestOption("1");
		XAVRequestAddressKeyFormat addressKeyFormat = new XAVRequestAddressKeyFormat();
		addressKeyFormat.setConsigneeName("test");
		addressKeyFormat.setRegion("ROSWELL,GA,30076-1521");
		addressKeyFormat.countryCode("CountryCode");
		ExecutionResult result = bpmClient.start().subProcess(startable).execute(1, "v1", "1", 1, requestData);
		var response = (XAVResponseWrapper) result.data().last().get("xavResponseWrapper");
		assertThat(response.getXaVResponse().getResponse().getResponseStatus().getCode()).isEqualTo("1");
		assertThat(response.getXaVResponse().getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
	}
}