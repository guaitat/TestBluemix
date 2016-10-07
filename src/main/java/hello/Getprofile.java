package hello;

import javax.inject.Inject;

import java.util.HashMap;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;

@Controller
@RequestMapping("/getprofile")
public class Getprofile {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public Getprofile (Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

 
    @RequestMapping(method=RequestMethod.GET,  produces="application/json")
    	 public @ResponseBody String getprofile(Model model) {
        
    	
    	System.out.println(connectionRepository.findPrimaryConnection(Twitter.class));
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        HashMap<Long, String> friendsIds = new HashMap<Long, String>();
        int i=0;
        int length = friends.size();
        while(i<length){
        	 TwitterProfile friend_iteration = friends.get(i);
        	 friendsIds.put(friend_iteration.getId(), friend_iteration.getName());
        	i++;
        }
       
        return friendsIds.toString();
        
    }

}
