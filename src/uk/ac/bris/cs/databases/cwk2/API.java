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
import uk.ac.bris.cs.databases.api.SimpleTopicView;
import uk.ac.bris.cs.databases.api.TopicView;

/**
 *
 * @author csxdb
 */
public class API implements APIProvider {

    private final Connection c;
    
    public API(Connection c) {
        this.c = c;
    }

    /* A.1 */
    
    @Override
    public Result<Map<String, String>> getUsers() {
        final String stmt = "SELECT username, name FROM Person;";
        Map<String, String> usermap = new LinkedHashMap<>();
        try(PreparedStatement s = c.prepareStatement(stmt)){
            ResultSet r = s.executeQuery();
            while(r.next()){
                usermap.put(r.getString("username"), r.getString("name"));
            }
            return Result.success(usermap);
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }
    }

    @Override
    public Result<PersonView> getPersonView(String username) {
        final String stmt = "SELECT name, username, stuId FROM Person WHERE username = ?";
        String name, studentID;
        if (username == null || username.isEmpty()){
            return Result.failure("getPersonView: username may not be empty");
        }
        try(PreparedStatement s = c.prepareStatement(stmt)){
            s.setString(1,username);
            ResultSet r = s.executeQuery();
            if (r.next()){
                name = r.getString("name");
                studentID = r.getString("stuId");
            }
            else{
                return Result.failure("getPersonView: Specified username does not exist");
            }
            PersonView pv;
            if (studentID != null) { pv = new PersonView(name, username, studentID); }
            else { pv = new PersonView(name, username, ""); }
            return Result.success(pv);
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }
    }
    
    @Override
    public Result addNewPerson(String name, String username, String studentId) {
        String stmt = "INSERT INTO Person (name, username, stuId)" + 
                            "VALUES (?, ?, ?)";
        final String nullstmt = "INSERT INTO Person (name, username)" + 
                            "VALUES (?, ?)";
        if (name == null || username == null || name.isEmpty() || username.isEmpty()){
            return Result.failure("addNewPerson: name and username must be filled");
        }
        if (studentId == null){ stmt = nullstmt; }
        try(PreparedStatement s = c.prepareStatement(stmt)){
            s.setString(1, name);
            s.setString(2, username);
            if (studentId != null){ s.setString(3, studentId); }
            if(s.executeUpdate() == 0){
                return Result.failure("addNewPerson: username already exists");
            }
            c.commit();
            return Result.success();
        } catch (SQLException e) {
            try{
                c.rollback();
            }
            catch (SQLException f){
                return Result.fatal(f.getMessage());
            }
            return Result.fatal(e.getMessage());
        }
    }
    
    /* A.2 */

    @Override
    public Result<List<SimpleForumSummaryView>> getSimpleForums() {
        final String stmt = "SELECT id, title FROM Forum";
        List<SimpleForumSummaryView> flist = new ArrayList<>();
        try(PreparedStatement s = c.prepareStatement(stmt)){
            ResultSet r = s.executeQuery();
            while(r.next()){
                flist.add(new SimpleForumSummaryView(r.getInt("id"), r.getString("title")));
            }
            return Result.success(flist);
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }
    }

    @Override
    public Result createForum(String title) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
    /* A.3 */
 
    @Override
    public Result<List<ForumSummaryView>> getForums() {
        throw new UnsupportedOperationException("Not supported yet.");
    }   
    
    @Override
    public Result<ForumView> getForum(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }   

    @Override
    public Result<SimpleTopicView> getSimpleTopic(int topicId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
    
    @Override
    public Result<PostView> getLatestPost(int topicId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result createPost(int topicId, String username, String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     
    @Override
    public Result createTopic(int forumId, String username, String title, String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Result<Integer> countPostsInTopic(int topicId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* B.1 */
       
    @Override
    public Result likeTopic(String username, int topicId, boolean like) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Result likePost(String username, int topicId, int post, boolean like) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<List<PersonView>> getLikers(int topicId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<TopicView> getTopic(int topicId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* B.2 */

    @Override
    public Result<List<AdvancedForumSummaryView>> getAdvancedForums() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<AdvancedPersonView> getAdvancedPersonView(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<AdvancedForumView> getAdvancedForum(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
