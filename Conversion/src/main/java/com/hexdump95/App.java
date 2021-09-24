package com.hexdump95;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String conversionValue = input.getPathParameters().get("decimal");

        if (!conversionValue.matches("([0-9]+)$"))
            return response.withStatusCode(400);

        int decimal = Integer.parseInt(conversionValue);

        String toBinary = Integer.toBinaryString(decimal);
        String toOctal = Integer.toOctalString(decimal);
        String toHexadecimal = Integer.toHexString(decimal);
        String output = String.format("{ " +
                "\"binary\": \"%s\", " +
                "\"octal\": \"%s\", " +
                "\"hexadecimal\": \"%s\" }", toBinary, toOctal, toHexadecimal);

        return response
                .withStatusCode(200)
                .withBody(output);
    }

}
