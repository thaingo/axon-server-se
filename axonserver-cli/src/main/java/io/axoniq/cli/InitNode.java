/*
 * Copyright (c) 2017-2019 AxonIQ B.V. and/or licensed to AxonIQ B.V.
 * under one or more contributor license agreements.
 *
 *  Licensed under the AxonIQ Open Source License Agreement v1.0;
 *  you may not use this file except in compliance with the license.
 *
 */

package io.axoniq.cli;

import io.axoniq.cli.json.RestResponse;
import org.apache.commons.cli.CommandLine;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * @author Marc Gathier
 */
public class InitNode extends AxonIQCliCommand {

    public static void run(String[] args) throws IOException {
        CommandLine commandLine = processCommandLine(args[0], args, CommandOptions.CONTEXT_TO_REGISTER_IN);
        String url = createUrl(commandLine, "/v1/context/init");
        if( commandLine.hasOption(CommandOptions.CONTEXT_TO_REGISTER_IN.getOpt())) {
            url += "?context=" + commandLine.getOptionValue(CommandOptions.CONTEXT_TO_REGISTER_IN.getOpt());
        }

        // get http client
        try (CloseableHttpClient httpclient = createClient(commandLine)) {
            postJSON(httpclient, url, null, 200, null, RestResponse.class);
        }
    }
}
