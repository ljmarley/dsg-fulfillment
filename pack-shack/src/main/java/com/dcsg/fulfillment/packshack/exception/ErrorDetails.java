package com.dcsg.fulfillment.packshack.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ErrorDetails {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private @Getter @Setter LocalDateTime timestamp;
	private @Getter @Setter String message;
	private @Getter @Setter String details;

}
