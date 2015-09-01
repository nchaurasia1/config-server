package com.taggstar.configapi.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Config.
 */
public class AppConfig {

    // Server config file properties
    private int port;
    private int maxThreads;
    private String awsSecretKey;
    private String awsAccessKey;
    private String awsRegion;
   
    // config filename
    private String fileName;

    private static final Log log = LogFactory.getLog(AppConfig.class);

    /**
     * Construct.
     * <p>
     * Call setFileName() and a load() method.
     */
    public AppConfig() {
    }

    /**
     * Construct instance.
     * <p>
     * Nothing is loaded during construction. To load properties call one of the load() methods.
     */
    public AppConfig(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Port for web server to use.
     *
     * @return Port for web server to use.
     */
    public int getPort() {
        return port;
    }

    /**
     * Max threads for web server to use.
     *
     * @return Max threads for web server to use.
     */
    public int getMaxThreads() {
        return maxThreads;
    }

    /**
     * AWS public key for server to use connecting to Dynamo.
     *
     * @return AWS public key for server to use connecting to Dynamo.
     */
    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    /**
     * AWS secret key for server to use connecting to Dynamo.
     *
     * @return AWS secret key for server to use connecting to Dynamo.
     */
    public String getAWSSecretKey() {
        return awsSecretKey;
    }

    /**
     * AWS region, e.g. eu-west-1
     *
     * @return AWS region name
     */
    public String getAwsRegion() {
        return awsRegion;
    }

    /**
     * Set properties filename used by loadServerProperties();
     *
     * @param fileName a filename
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    
    /**
     * Load server from a specified properties file.
     *
     * @throws IOException
     */
    public void loadServerProperties() throws IOException {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);

        Properties props = new Properties();
        props.load(is);

        loadServerProperties(props);

    }

    /**
     * Load server properties
     *
     * @param props server properties
     * @throws IOException
     */
    public void loadServerProperties(Properties props) throws IOException {

        // AWS

        this.awsSecretKey = (String) props.get("aws.secret.key");
        log.info("aws.secret.key=******"); // don't put key in log file.

        this.awsAccessKey = (String) props.get("aws.access.key");
        log.info("aws.access.key=" + this.awsAccessKey);

        this.awsRegion = (String) props.get("aws.region");
        log.info("aws.region=" + this.awsRegion);
    
        // Web server

        this.port = Integer.valueOf((String) props.get("web.server.port"));
        log.info("web.server.port=" + this.port);

        this.maxThreads = Integer.valueOf((String)props.get("web.server.max.threads"));
        log.info("web.server.max.threads=" + this.maxThreads);


    }

}