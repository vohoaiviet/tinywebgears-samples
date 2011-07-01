package com.tinywebgears.samples.service;

public class SampleServiceImpl implements SampleService
{
    private final String environmentName;
    private final String productName;
    private final String productVersion;
    private final String svnRevision;

    public SampleServiceImpl(String environmentName, String productName, String productVersion, String svnRevision)
    {
        this.environmentName = environmentName;
        this.productName = productName;
        this.productVersion = productVersion;
        this.svnRevision = svnRevision;
    }

    @Override
    public String getVersion()
    {
        return productName + " (version: " + productVersion + ", SVN revision: " + svnRevision
                + ", deployment environment: " + environmentName + ")";
    }
}
