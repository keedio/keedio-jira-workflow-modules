package it.com.keedio.jira.workflow;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.keedio.jira.workflow.KeedioWorkflowModulesMainComponent;
import com.atlassian.sal.api.ApplicationProperties;

import static org.junit.Assert.assertEquals;

@RunWith(AtlassianPluginsTestRunner.class)
public class MyComponentWiredTest
{
    private final ApplicationProperties applicationProperties;
    private final KeedioWorkflowModulesMainComponent keedioWorkflowModulesMainComponent;

    public MyComponentWiredTest(ApplicationProperties applicationProperties,KeedioWorkflowModulesMainComponent keedioWorkflowModulesMainComponent)
    {
        this.applicationProperties = applicationProperties;
        this.keedioWorkflowModulesMainComponent = keedioWorkflowModulesMainComponent;
    }

    @Test
    public void testMyName()
    {
        assertEquals("names do not match!", "myComponent:" + applicationProperties.getDisplayName(), keedioWorkflowModulesMainComponent.getName());
    }
}