package com.axonivy.connector.ups.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.security.PermitAll;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import io.swagger.v3.oas.annotations.Hidden;

@Path("upsMock")
@PermitAll
@Hidden
public class UPSMock {
	@GET
	@Path("track/v1/details/{inquiryNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTracking(@PathParam("inquiryNumber") String inquiryNumber, @PathParam("locale") String locale,
			@PathParam("returnSignature") String returnSignature) {
		return Response.status(200).entity(load("CreateTrackingResponse.json")).build();
	}

	@DELETE
	@Path("shipments/{version}/pickup/{cancelBy}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pickupCancel(@PathParam("cancelBy") String cancelBy, @PathParam("version") String version) {
		return Response.status(200).entity("{\r\n"
				+ "    \"PickupCancelResponse\": {\r\n"
				+ "        \"Response\": {\r\n"
				+ "            \"ResponseStatus\": {\r\n"
				+ "                \"Code\": \"1\",\r\n"
				+ "                \"Description\": \"Success\"\r\n"
				+ "            },\r\n"
				+ "            \"TransactionReference\": {\r\n"
				+ "                \"CustomerContext\": \"testing\",\r\n"
				+ "                \"TransactionIdentifier\": \"iewssoat2dtc6rrV5x2prt\"\r\n"
				+ "            }\r\n"
				+ "        },\r\n"
				+ "        \"PickupType\": \"01\"\r\n"
				+ "    }\r\n"
				+ "}")
				.build();
	}
	
	@GET
	@Path("shipments/{version}/pickup/{pickupType}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pickupPendingStatus(@PathParam("pickupType") String pickupType,
			@PathParam("version") String version) {
		return Response.status(200).entity("{\r\n"
				+ "    \"PickupPendingStatusResponse\": {\r\n"
				+ "        \"Response\": {\r\n"
				+ "            \"ResponseStatus\": {\r\n"
				+ "                \"Code\": \"1\",\r\n"
				+ "                \"Description\": \"Success\"\r\n"
				+ "            },\r\n"
				+ "            \"TransactionReference\": {\r\n"
				+ "                \"CustomerContext\": \"testing\",\r\n"
				+ "                \"TransactionIdentifier\": \"iewssoas21bcKvQwVSC7b5\"\r\n"
				+ "            }\r\n"
				+ "        },\r\n"
				+ "        \"PendingStatus\": {\r\n"
				+ "            \"PickupType\": \"01\",\r\n"
				+ "            \"ServiceDate\": \"20240110\",\r\n"
				+ "            \"PRN\": \"2929AONCALL\",\r\n"
				+ "            \"OnCallStatusCode\": \"001\",\r\n"
				+ "            \"PickupStatusMessage\": \"Received at dispatch\",\r\n"
				+ "            \"BillingCode\": \"01\",\r\n"
				+ "            \"ContactName\": \"Shipping Mgr.\",\r\n"
				+ "            \"ReferenceNumber\": \"OnCallNextDayAir\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}")
				.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addressvalidation/{version}/{requestOption}")
	public Response addressValidation(@PathParam("version") String version,
			@PathParam("requestOption") String requestOption) {
		return Response.status(200).entity("{\r\n"
				+ "    \"XAVResponse\": {\r\n"
				+ "        \"Response\": {\r\n"
				+ "            \"ResponseStatus\": {\r\n"
				+ "                \"Code\": \"1\",\r\n"
				+ "                \"Description\": \"Success\"\r\n"
				+ "            }\r\n"
				+ "        },\r\n"
				+ "        \"NoCandidatesIndicator\": \"\"\r\n"
				+ "    }\r\n"
				+ "}")
				.build();
	}
	
	private static String load(String json) {
		try (var is = UPSMock.class.getResourceAsStream("json/" + json)) {
			if (is == null) {
				throw new RuntimeException("The json file '" + json + "' does not exist.");
			}
			return IOUtils.toString(is, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to read json " + json, ex);
		}
	}
}
