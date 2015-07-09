package com.keedio.jira.workflow.condition;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchProvider;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.ApplicationUsers;
import com.atlassian.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.workflow.condition.AbstractJiraCondition;
import com.opensymphony.module.propertyset.PropertySet;

import java.util.Map;

public class AssignedIssuesPerUserLimitWorkflowCondition extends AbstractJiraCondition
{
    private static final Logger log = LoggerFactory.getLogger(AssignedIssuesPerUserLimitWorkflowCondition.class);
    public static final String MAX_ASSIGNED_ISSUES_PER_USER ="maxAssignedIssuesPerUser";

    private SearchProvider searchProvider;

    public AssignedIssuesPerUserLimitWorkflowCondition(SearchProvider searchProvider) {
        this.searchProvider = searchProvider;
    }

    public boolean passesCondition(Map transientVars, Map args, PropertySet ps)
    {
        long maxAssignedIssuesParam = 0;
        try {
            log.info("Max admitted issues assigned per user: " + args.get(MAX_ASSIGNED_ISSUES_PER_USER));

            maxAssignedIssuesParam = Long.parseLong((String)args.get(MAX_ASSIGNED_ISSUES_PER_USER));
        } catch (NumberFormatException e) {
            log.error("Exception", e);

            throw new RuntimeException(e);
        }

        Issue issue = getIssue(transientVars);

        log.info("Issue: "+ issue.getKey());

        boolean isAssigned = issue.getAssignee() != null && issue.getAssignee().isActive();

        if (!isAssigned){
            log.debug("Issue has not been assigned yet! Cannot transition to the final state.");
            return false;
        }

        JiraAuthenticationContext jiraAuthenticationContext =
                ComponentAccessor.getJiraAuthenticationContext();

        ApplicationUser loggedInUser = jiraAuthenticationContext.getUser();
        log.info("LoggedUser: " + loggedInUser.getUsername());

        ApplicationUser assignee = ApplicationUsers.from(issue.getAssigneeUser());
        log.info("Assignee: "+assignee.getUsername());

        Query q = JqlQueryBuilder.newBuilder()
                .where()
                    .assigneeUser(assignee.getUsername())
                    .and()
                    .status("In Progress")
                .buildQuery();

        log.info("Query: "+q.toString());

        //  ComponentAccessor.getIssueIndexManager().getIssueSearcher().search();

        try {
            long assigned = searchProvider.searchCount(q, loggedInUser);

            log.info("Number of assigned issues: " + assigned);

            return assigned < maxAssignedIssuesParam;
        } catch (SearchException e) {
            log.error("Exception", e);
            throw new RuntimeException(e);
        }
    }
}
