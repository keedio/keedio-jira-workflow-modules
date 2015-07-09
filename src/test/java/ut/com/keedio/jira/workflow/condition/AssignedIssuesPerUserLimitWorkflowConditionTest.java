package ut.com.keedio.jira.workflow.condition;

import com.atlassian.crowd.embedded.api.User;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.atlassian.jira.exception.DataAccessException;

import com.atlassian.jira.issue.MutableIssue;

import java.util.HashMap;
import java.util.Map;

import com.keedio.jira.workflow.condition.AssignedIssuesPerUserLimitWorkflowCondition;

public class AssignedIssuesPerUserLimitWorkflowConditionTest
{
    public static final String FIELD_MAX_ASSIGNED_ISSUES_PER_USER ="maxAssignedIssuesPerUser";

    protected AssignedIssuesPerUserLimitWorkflowCondition condition;
    protected MutableIssue issue;

    @Before
    public void setup() {

        issue = createPartialMockedIssue();

        condition = new AssignedIssuesPerUserLimitWorkflowCondition(null) {
            protected MutableIssue getIssue(Map transientVars) throws DataAccessException {
                return issue;
            }
        };
    }

    @Test
    public void testPassesCondition() throws Exception
    {
        Map transientVars = new HashMap();
        transientVars.put(FIELD_MAX_ASSIGNED_ISSUES_PER_USER, "1");
        when(issue.getDescription()).thenReturn("This description has test in it.");

        assertTrue("condition should pass",condition.passesCondition(transientVars,null,null));
    }

    @Test
    public void testFailsCondition() throws Exception
    {
        Map transientVars = new HashMap();
        transientVars.put(FIELD_MAX_ASSIGNED_ISSUES_PER_USER,"-1");
        when(issue.getDescription()).thenReturn("This description does not have the magic word in it.");

        assertFalse("condition should fail",condition.passesCondition(transientVars,null,null));
    }

    private MutableIssue createPartialMockedIssue() {
        MutableIssue mockIssue = mock(MutableIssue.class);
        User mockUser = mock(User.class);
        when(mockUser.isActive()).thenReturn(true);
        when(mockIssue.getAssignee()).thenReturn(mockUser);
        return mockIssue;
        /*
        GenericValue genericValue = mock(GenericValue.class);
        IssueManager issueManager = mock(IssueManager.class);
        ProjectManager projectManager = mock(ProjectManager.class);
        VersionManager versionManager = mock(VersionManager.class);
        IssueSecurityLevelManager issueSecurityLevelManager = mock(IssueSecurityLevelManager.class);
        ConstantsManager constantsManager = mock(ConstantsManager.class);
        SubTaskManager subTaskManager = mock(SubTaskManager.class);
        AttachmentManager attachmentManager = mock(AttachmentManager.class);
        LabelManager labelManager = mock(LabelManager.class);
        ProjectComponentManager projectComponentManager = mock(ProjectComponentManager.class);
        UserManager userManager = mock(UserManager.class);

        return new IssueImpl(genericValue, issueManager, projectManager, versionManager, issueSecurityLevelManager, constantsManager, subTaskManager, attachmentManager, labelManager, projectComponentManager, userManager);
        */
    }

}
