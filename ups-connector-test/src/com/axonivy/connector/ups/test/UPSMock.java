package com.axonivy.connector.ups.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
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

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("addressvalidation/{version}/{requestOption}")
  public Response addressValidation(@PathParam("version") String version,
      @PathParam("requestOption") String requestOption) {
    return Response.status(200).entity(load("AddressValidation.json")).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("locations/{version}/search/availabilities/{reqOption}")
  public Response locator(@PathParam("version") String version, @PathParam("reqOption") String requestOption) {
    return Response.status(200).entity(load("GetLocator.json")).build();
  }

  @DELETE
  @Path("paperlessdocuments/{version}/DocumentId/ShipperNumber")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePaperless(@PathParam(value = "version") String version) {
    return Response.status(200).entity(load("DeletePaperlessDocument.json")).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("paperlessdocuments/{version}/image")
  public Response postImage(@PathParam("version") String version) {
    return Response.status(201).entity(load("UploadPaperlesImage.json")).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("paperlessdocuments/{version}/upload")
  public Response postDocument(@PathParam("version") String version) {
    return Response.status(201).entity(load("UploadPaperlessDocument.json")).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("pickupcreation/{version}/pickup")
  public Response pickupCreate(@PathParam(value = "version") String version) {
    return Response.status(200).entity(load("PickupCreation.json")).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("pickup/{version}/servicecenterlocations")
  public Response addressValidation(@PathParam("version") String version) {
    return Response.status(200).entity(load("AddressValidation.json")).build();
  }

  @GET
  @Path("track/v1/details/{inquiryNumber}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTracking(@PathParam("inquiryNumber") String inquiryNumber, @PathParam("locale") String locale,
      @PathParam("returnSignature") String returnSignature) {
    return Response.status(200).entity(load("CreateTrackingResponse.json")).build();
  }

  @GET
  @Path("shipments/{version}/pickup/{pickupType}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response pickupPendingStatus(@PathParam("pickupType") String pickupType,
      @PathParam("version") String version) {
    return Response.status(200).entity(load("PickupPending.json")).build();
  }

  @DELETE
  @Path("shipments/{version}/pickup/{cancelBy}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response pickupCancel(@PathParam("cancelBy") String cancelBy, @PathParam("version") String version) {
    return Response.status(200).entity(load("PickupCancel.json")).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("dangerousgoods/{version}/prenotification")
  public Response preNotification(@PathParam("version") String version) {
    return Response.status(200).entity(load("PreNoti.json")).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("quantumview/{version}/events")
  public Response quantumView(@PathParam("version") String version) {
    return Response.status(200).entity(load("QuantumView.json")).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("shipments/{version}/transittimes")
  public Response transitTime(@PathParam("version") String version) {
    return Response.status(201).entity(load("TimeTransit.json")).build();
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
