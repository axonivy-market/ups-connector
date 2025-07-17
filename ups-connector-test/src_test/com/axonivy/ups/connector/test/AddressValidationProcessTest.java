package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.ups.wwwcie.api.client.XAVRequest;
import com.ups.wwwcie.api.client.XAVRequestAddressKeyFormat;
import com.ups.wwwcie.api.client.XAVRequestRequest;
import com.ups.wwwcie.api.client.XAVResponseWrapper;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;


//@IvyProcessTest(enableWebServer = true)
//@ExtendWith(MultiEnvironmentContextProvider.class)
public class AddressValidationProcessTest extends BaseSetup {

//	@BeforeEach
//	void setup(ExtensionContext context, AppFixture fixture) {
//		setupEnvironmentForTesting(context, fixture);
//	}

	@TestTemplate
	void addressValidation(ExtensionContext context,BpmClient bpmClient) throws NoSuchFieldException {
		BpmElement startable = ADDRESS_VALIDATION.elementName("addressValidation(Integer,String,String,Integer,XAVRequest)");
		XAVRequest requestData = new XAVRequest();
		requestData.setRequest(new XAVRequestRequest());
		requestData.getRequest().setRequestOption("1");
		XAVRequestAddressKeyFormat addressKeyFormat = new XAVRequestAddressKeyFormat();
		addressKeyFormat.setConsigneeName("test");
		addressKeyFormat.setRegion("ROSWELL,GA,30076-1521");
		addressKeyFormat.countryCode("CountryCode");
		ExecutionResult result = bpmClient.start().subProcess(startable).execute(1, "v1", "1", 1, requestData);
		var response = (XAVResponseWrapper) result.data().last().get("xavResponseWrapper");
		if (response != null) {
			assertThat(response.getXaVResponse().getResponse().getResponseStatus().getCode()).isEqualTo("1");
			assertThat(response.getXaVResponse().getResponse().getResponseStatus().getDescription())
					.isEqualTo("Success");
		} else {
			assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
		}
	}
}