package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;
import com.axonivy.ups.connector.test.context.MultiEnvironmentContextProvider;

import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.error.BpmError;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;
@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class BaseSetup {
  protected static final BpmProcess ADDRESS_VALIDATION = BpmProcess.path("AddressValidation");
  protected static final BpmProcess LOCATOR = BpmProcess.path("Locator");
  protected static final BpmProcess PAPERLESS_DOCUMENTS = BpmProcess.path("PaperlessDocuments");
  protected static final BpmProcess PICKUP = BpmProcess.path("Pickup");
  protected static final BpmProcess PRE_NOTIFICATION = BpmProcess.path("PreNotification");
  protected static final BpmProcess QUATUM_VIEW = BpmProcess.path("QuatumView");
  protected static final BpmProcess SHIPPING = BpmProcess.path("Shipping");
  protected static final BpmProcess TIME_IN_TRANSIT = BpmProcess.path("TimeInTransit");
  protected static final BpmProcess TRACKING = BpmProcess.path("Tracking");

  @BeforeEach
  void setupEnvironmentForTesting(ExtensionContext context, AppFixture fixture) {
    fixture.var("upsConnector.appId", "appId");
    fixture.var("upsConnector.secretKey", "secretKey");;

    switch (context.getDisplayName()) {
    case UpsConnectorTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME:
      fixture.var("upsConnector.Url", "https://wwwcie.ups.com/api");
      fixture.var("upsConnector.authUri", "https://wwwcie.ups.com/security/v1/oauth");
      break;
    case UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME:
      fixture.config(String.format("RestClients.%s.Features", getCurrentRestClientFeaturesConfig()),
          List.of("ch.ivyteam.ivy.rest.client.mapper.JsonFeature"));
      fixture.config(String.format("RestClients.%s.Url", getCurrentRestClientFeaturesConfig()),
          "{ivy.app.baseurl}/api/upsMock");
      System.out.print(String.format("RestClients.%s.Url", getCurrentRestClientFeaturesConfig()));
      fixture.var("upsConnector.Url", "{ivy.app.baseurl}/api/upsMock");
      break;
    default:
      break;
    }
  }

  protected String getCurrentRestClientFeaturesConfig() {
    return "";
  }

  protected void assertAcceptableHttpStatusResponse(String contextName, ExecutionResult result)
      throws NoSuchFieldException {
    BpmError error = (BpmError) result.data().last().get("error");
    int actualStatus = error.getHttpStatusCode();
    assertThat(actualStatus).isNotNull();
    assertThat(contextName).isEqualToIgnoringCase(UpsConnectorTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME);
    assertThat(isAcceptableStatus(actualStatus)).withFailMessage("Unexpected HTTP status: %s", actualStatus).isTrue();
  }

  private boolean isAcceptableStatus(int statusCode) {
    return statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_UNAUTHORIZED
        || statusCode == HttpStatus.SC_FORBIDDEN || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR;
  }
}
