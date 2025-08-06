package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;
import com.ups.wwwcie.api.client.DeleteResponse;
import com.ups.wwwcie.api.client.PushToImageRepositoryRequest;
import com.ups.wwwcie.api.client.PushToImageRepositoryResponse;
import com.ups.wwwcie.api.client.UploadRequest;
import com.ups.wwwcie.api.client.UploadResponse;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;

public class PaperLessDocumentProcessTest extends BaseSetup {

  @Override
  protected String getCurrentRestClientFeaturesConfig() {
    return "ups (Paperless Document)";
  }

  @TestTemplate
  void testDeletePaperlessDocument(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PAPERLESS_DOCUMENTS.elementName("deletePaperlessDocument(String)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("test");
    var response = (DeleteResponse) result.data().last().get("deleteResponse");
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
      assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }

  @TestTemplate
  void testPaperlessDocumentPushImage(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PAPERLESS_DOCUMENTS
        .elementName("paperlessDocumentPushImage(String,String,String,String,PushToImageRepositoryRequest)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("test", "test", "test", "test",
        new PushToImageRepositoryRequest());
    var response = (PushToImageRepositoryResponse) result.data().last().get("pushToImageRepositoryResponse");
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
      assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }

  @TestTemplate
  void testUploadPaperlessDocument(ExtensionContext context, BpmClient bpmClient) throws NoSuchFieldException {
    BpmElement startable = PAPERLESS_DOCUMENTS
        .elementName("uploadPaperlessDocument(String,String,String,String,UploadRequest)");
    ExecutionResult result = bpmClient.start().subProcess(startable).execute("test", "test", "test", "test",
        new UploadRequest());
    var response = (UploadResponse) result.data().last().get("uploadResponse");
    if (UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME.equals(context.getDisplayName())) {
      assertThat(response.getResponse().getResponseStatus().getCode()).isEqualTo("1");
      assertThat(response.getResponse().getResponseStatus().getDescription()).isEqualTo("Success");
    } else {
      assertAcceptableHttpStatusResponse(context.getDisplayName(), result);
    }
  }
}
