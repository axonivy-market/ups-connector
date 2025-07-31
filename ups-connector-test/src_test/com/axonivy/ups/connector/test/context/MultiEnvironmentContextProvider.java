package com.axonivy.ups.connector.test.context;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.axonivy.connector.ups.test.constant.UpsConnectorTestConstants;

public class MultiEnvironmentContextProvider implements TestTemplateInvocationContextProvider {

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
		String testEnv = System.getProperty(UpsConnectorTestConstants.END_TO_END_TESTING_ENVIRONMENT_KEY);
		return switch (testEnv) {
		case UpsConnectorTestConstants.END_TO_END_TESTING_ENVIRONMENT_VALUE ->
			Stream.of(new TestEnvironmentInvocationContext(UpsConnectorTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME));
		default ->
			Stream.of(new TestEnvironmentInvocationContext(UpsConnectorTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME));
		};
	}

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		return true;
	}
}
