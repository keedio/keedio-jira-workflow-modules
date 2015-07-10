package com.keedio.jira.workflow.condition;

import com.atlassian.core.util.map.EasyMap;
import com.atlassian.jira.plugin.workflow.AbstractWorkflowPluginFactory;
import com.atlassian.jira.plugin.workflow.WorkflowPluginConditionFactory;
import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.ConditionDescriptor;

import java.util.Map;

/*
This is the factory class responsible for dealing with the UI for the post-function.
This is typically where you put default values into the velocity context and where you store user input.
 */

public class AssignedIssuesPerUserLimitWorkflowConditionFactory extends AbstractWorkflowPluginFactory implements WorkflowPluginConditionFactory
{
    public static final String MAX_ASSIGNED_ISSUES_PER_USER = "maxAssignedIssuesPerUser";
    public static final String FILTER_STATUS = "filterStatus";

    protected void getVelocityParamsForInput(Map velocityParams)
    {
        //the default message
        velocityParams.put(MAX_ASSIGNED_ISSUES_PER_USER,"1");
        velocityParams.put(FILTER_STATUS,"In Progress");
    }

    protected void getVelocityParamsForEdit(Map velocityParams, AbstractDescriptor descriptor)
    {
        getVelocityParamsForInput(velocityParams);
        getVelocityParamsForView(velocityParams, descriptor);
    }

    protected void getVelocityParamsForView(Map velocityParams, AbstractDescriptor descriptor)
    {
        if (!(descriptor instanceof ConditionDescriptor))
        {
            throw new IllegalArgumentException("Descriptor must be a ConditionDescriptor.");
        }

        ConditionDescriptor conditionDescriptor = (ConditionDescriptor) descriptor;

        velocityParams.put(MAX_ASSIGNED_ISSUES_PER_USER, conditionDescriptor.getArgs().get(MAX_ASSIGNED_ISSUES_PER_USER));
        velocityParams.put(FILTER_STATUS, conditionDescriptor.getArgs().get(FILTER_STATUS));
    }

    public Map getDescriptorParams(Map conditionParams)
    {
        // Process The map
        String maxNumIssues = extractSingleParam(conditionParams, MAX_ASSIGNED_ISSUES_PER_USER);
        String filterStatus = extractSingleParam(conditionParams, FILTER_STATUS);
        return EasyMap.build(MAX_ASSIGNED_ISSUES_PER_USER, maxNumIssues, FILTER_STATUS, filterStatus);
    }
}
