package com.axonivy.ups.connector.test;

import static org.assertj.core.api.Assertions.assertThat;
import static com.axonivy.utils.e2etest.enums.E2EEnvironment.MOCK_SERVER;
import static com.axonivy.utils.e2etest.enums.E2EEnvironment.REAL_SERVER;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.ups.connector.test.feature.MockCallHeaderFeature;
import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;
import com.axonivy.utils.e2etest.utils.E2ETestUtils;

import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.error.BpmError;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.rest.client.mapper.JsonFeature;

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
  protected boolean isMockTest;

  @BeforeEach
  void setupEnvironmentForTesting(ExtensionContext context, AppFixture fixture) {
    isMockTest = context.getDisplayName().equals(MOCK_SERVER.getDisplayName());
    E2ETestUtils.determineConfigForContext(context.getDisplayName(), runRealEnv(fixture), runMockEnv(fixture));
  }

  private Runnable runRealEnv(AppFixture fixture) {
    return () -> {
      fixture.var("upsConnector.appId", "appId");
      fixture.var("upsConnector.secretKey", "secretKey");
      fixture.var("upsConnector.Url", "https://wwwcie.ups.com/api");
      fixture.var("upsConnector.authUri", "https://wwwcie.ups.com/security/v1/oauth");
    };
  }

  private Runnable runMockEnv(AppFixture fixture) {
    return () -> {
      fixture.config(String.format("RestClients.%s.Features", getCurrentRestClientFeaturesConfig()),
          List.of(MockCallHeaderFeature.class.getName(), JsonFeature.class.getName()));
      fixture.config(String.format("RestClients.%s.Url", getCurrentRestClientFeaturesConfig()),
          "{ivy.app.baseurl}/api/upsMock");
      fixture.var("upsConnector.Url", "{ivy.app.baseurl}/api/upsMock");
    };
  }

  protected String getCurrentRestClientFeaturesConfig() {
    return "";
  }

  protected void assertAcceptableHttpStatusResponse(String contextName, ExecutionResult result)
      throws NoSuchFieldException {
    BpmError error = (BpmError) result.data().last().get("error");
    int actualStatus = error.getHttpStatusCode();
    assertThat(actualStatus).isNotNull();
    assertThat(contextName).isEqualToIgnoringCase(REAL_SERVER.getDisplayName());
    assertThat(isAcceptableStatus(actualStatus)).withFailMessage("Unexpected HTTP status: %s", actualStatus).isTrue();
  }

  private boolean isAcceptableStatus(int statusCode) {
    return statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_UNAUTHORIZED
        || statusCode == HttpStatus.SC_FORBIDDEN || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR;
  }
}
