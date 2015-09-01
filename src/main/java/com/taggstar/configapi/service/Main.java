package com.taggstar.configapi.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.thread.QueuedThreadPool;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.taggstar.configapi.controllers.ConfigController;

/**
 * Report service HTTP server.
 */
public class Main {

    private org.mortbay.jetty.Server server;
    private static final Log log = LogFactory.getLog(Main.class);

    /**
     * Run web application on port defined in Config object.
     * <p>
     * Call start() to make server ready to process requests.
     *
     * @throws Exception
     */
    public Main(AppConfig appConfig) throws Exception {

        log.info("starting");

        // Construct the application's top level objects and wire them together

        // AWS connections
        AWSCredentials awsCredential = new BasicAWSCredentials(appConfig.getAwsAccessKey(), appConfig.getAWSSecretKey());
        AmazonSQSAsyncClient sqsClient  = new AmazonSQSAsyncClient(awsCredential);
        ConfigController configController = new ConfigController(appConfig, sqsClient);

        HttpHandler handler = new HttpHandler(configController);

        // Setup Jetty

        server = new org.mortbay.jetty.Server(appConfig.getPort());
        server.setHandler(handler);

        // Start Jetty
        start(appConfig.getMaxThreads());

    }

    /**
     * Start Jetty server.
     *
     * @throws Exception on error
     */
    public void start(int maxThreads) throws Exception {

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads);
        server.setThreadPool(threadPool);
        server.start();
        log.info("started_web_server max_threads=[" + maxThreads + "]");

    }

    /**
     * Start web server from command line using config.properties or specified file.
     *
     * @param args optional filename of config file
     * @throws Exception on errors
     */
    public static void main(String[] args) throws Exception {

        AppConfig config = null;
        if (args.length == 0) {

            config = new AppConfig("config.properties");
            config.loadServerProperties();

        } else if (args.length == 1) {

            config = new AppConfig(args[0]);
            config.loadServerProperties();

        } else {
            exitOnUsage();
        }

        new Main(config);

    }

    //
    // Internal
    //

    private static void exitOnUsage() {
        System.out.println("");
        System.out.println("Usage : [filename] ; optional config filename (default config.properties)");
        System.exit(-1);
    }
}
