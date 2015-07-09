package com.keedio.jira.workflow;

import com.atlassian.sal.api.ApplicationProperties;

public class KeedioWorkflowModulesMainComponentImpl implements KeedioWorkflowModulesMainComponent
{
    private final ApplicationProperties applicationProperties;

    public KeedioWorkflowModulesMainComponentImpl(ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    public String getName()
    {
        if(null != applicationProperties)
        {
            return "myComponent:" + applicationProperties.getDisplayName();
        }
        
        return "myComponent";
    }
}