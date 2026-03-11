package np.com.onlyrj.LibraryManagementSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/check")
    public String home(){
        return "All ok...";
    }
}
