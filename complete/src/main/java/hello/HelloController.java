package hello;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FriendList;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public HelloController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        System.out.println("LA CONEXION"+connectionRepository.toString());
        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        User user = facebook.userOperations().getUserProfile();
        model.addAttribute(user);
        System.out.println("El usuario"+user.getGender());
        return "hello";
        /* requiere autorizar acceso a amigos?
        PagedList<FriendList> friends = facebook.friendOperations().getFriendLists() ;        
        model.addAttribute("friends", friends);
        System.out.println("lista de amigos: " + friends.size());
        System.out.println("lista de amigos vacia??: " + friends.isEmpty());
        return "hello";
        */
    }

}
