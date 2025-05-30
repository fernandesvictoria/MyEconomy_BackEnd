package com.me.myEconomy.exception;

import org.springframework.http.HttpStatus;

public class MeException extends Exception {
	public MeException(String message, HttpStatus badRequest) {
		super(message);
	}
}