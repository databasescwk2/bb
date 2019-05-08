/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.cs.databases.cwk2;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.AdvancedForumSummaryView;
import uk.ac.bris.cs.databases.api.AdvancedForumView;
import uk.ac.bris.cs.databases.api.ForumSummaryView;
import uk.ac.bris.cs.databases.api.ForumView;
import uk.ac.bris.cs.databases.api.AdvancedPersonView;
import uk.ac.bris.cs.databases.api.PostView;
import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.PersonView;
import uk.ac.bris.cs.databases.api.SimpleForumSummaryView;
import uk.ac.bris.cs.databases.api.SimpleTopicSummaryView;
import uk.ac.bris.cs.databases.api.SimpleTopicView;
import uk.ac.bris.cs.databases.api.TopicView;

/**
 *
 * @author heman
 */
public class SummaryViewGetter {
    private final Connection c;
    
    public SummaryViewGetter(Connection c) {
        this.c = c;
    }
    
    Result<SimpleTopicSummaryView> lastSimpleTopic(int forumId){
        final String stmt = "SELECT Topic.id as topicId, Forum.id as forumId, Topic.title " + 
                            "FROM Topic INNER JOIN Forum ON forumId=Forum.id " +
                            "WHERE forumId = ? " +
                            "ORDER BY Topic.id DESC LIMIT 1";
        SimpleTopicSummaryView lastTopic;
        try(PreparedStatement s = c.prepareStatement(stmt)){
            s.setInt(1, forumId);
            ResultSet r = s.executeQuery();
            if(r.next() == false){
                return Result.success(null);
            }
            lastTopic = new SimpleTopicSummaryView(r.getInt("topicId"),forumId, r.getString("title"));
            return Result.success(lastTopic);
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }
    }

    Result<List<SimpleTopicSummaryView>> topicList(int forumId){
        final String stmt = "SELECT Topic.id as topicId, Forum.id as forumId, Topic.title " +
                "FROM Topic INNER JOIN Forum ON forumId=Forum.id " +
                "WHERE forumId = ? " +
                "ORDER BY Topic.id DESC";

        SimpleTopicSummaryView Topic;
        List<SimpleTopicSummaryView> tl = new ArrayList<>();

        try(PreparedStatement s = c.prepareStatement(stmt)){
            s.setInt(1, forumId);
            ResultSet r = s.executeQuery();
            while(r.next()){
                Topic = new SimpleTopicSummaryView(r.getInt("topicId"),forumId, r.getString("title"));
                tl.add(Topic);
            }

            return Result.success(tl);
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }

    }
}
