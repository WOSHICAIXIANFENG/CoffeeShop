package edu.mum.coffee.rest;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class TestErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
    	//conversion logic for decoding conversion
    	Scanner s = new Scanner(response.getBody()).useDelimiter("\\A");
    	String result = s.hasNext() ? s.next() : "";
    
    	System.out.println("Response result = " + result);
    }
}