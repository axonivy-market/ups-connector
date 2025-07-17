package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.ups.wwwcie.api.client.DeleteResponse;
import com.ups.wwwcie.api.client.XAVRequest;
import com.ups.wwwcie.api.client.XAVRequestAddressKeyFormat;
import com.ups.wwwcie.api.client.XAVRequestRequest;
import com.ups.wwwcie.api.client.XAVResponseWrapper;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.environment.AppFixture;

public class PaperLessDocumentProcessTest extends BaseSetup {

	@TestTemplate
	void testDeletePaperlessDocument(ExtensionContext context,BpmClient bpmClient) throws NoSuchFieldException {
		BpmElement startable = PAPERLESS_DOCUMENTS.elementName("deletePaperlessDocument()");
		ExecutionResult result = bpmClient.start().subProcess(startable).execute();
		var response = (DeleteResponse) result.data().last().get("deleteResponse");
		if (response != null) {
			assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
			assertThat(response.getResponse().getResponseStatus().getDescription())
					.isEqualTo("Success");
		} else {
			assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
		}
	}
}
