package ut.com.keedio.jira.workflow;

import org.junit.Test;
import com.keedio.jira.workflow.KeedioWorkflowModulesMainComponent;
import com.keedio.jira.workflow.KeedioWorkflowModulesMainComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        KeedioWorkflowModulesMainComponent component = new KeedioWorkflowModulesMainComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}