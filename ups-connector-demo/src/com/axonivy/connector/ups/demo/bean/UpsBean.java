package com.axonivy.connector.ups.demo.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import com.ups.wwwcie.api.client.PickupCreationRequestPickupPiece;
import com.ups.wwwcie.api.client.XAVRequestAddressKeyFormat;

@ViewScoped
@Named
public class UpsBean implements Serializable {
	private final DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
	private final DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
	private final DateFormat inputDateFormatter = new SimpleDateFormat("yyyyMMdd");
	private final DateFormat inputTimeFormatter = new SimpleDateFormat("HHmmss");
	private XAVRequestAddressKeyFormat addressKeyFormat = new XAVRequestAddressKeyFormat();
	private PickupCreationRequestPickupPiece pickupPiece = new PickupCreationRequestPickupPiece();

	public String getFormattedDate(String date) throws ParseException {
		return dateFormatter.format(inputDateFormatter.parse(date));
	}

	public String getFormattedTime(String time) throws ParseException {
		return timeFormatter.format(inputTimeFormatter.parse(time));
	}

	public DateFormat getTimeFormatter() {
		return timeFormatter;
	}

	public DateFormat getDateFormatter() {
		return dateFormatter;
	}

	public XAVRequestAddressKeyFormat getAddressKeyFormat() {
		return addressKeyFormat;
	}

	public void setAddressKeyFormat(XAVRequestAddressKeyFormat addressKeyFormat) {
		this.addressKeyFormat = addressKeyFormat;
	}

	public PickupCreationRequestPickupPiece getPickupPiece() {
		return pickupPiece;
	}

	public void setPickupPiece(PickupCreationRequestPickupPiece pickupPiece) {
		this.pickupPiece = pickupPiece;
	}
}
