package com.ikg.integrationwithkudago.controllers;

import com.ikg.integrationwithkudago.services.GetAllEventsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*****

 @author Sergei Iurochkin
 @link https://github.com/SilverRid

 */
@Controller
@RequestMapping("/")
public class ControllerIKG {
    @Autowired
    private GetAllEventsServices getAllEventsServices;
    @GetMapping("/events")
    public String getAllEventsRequest(@RequestParam(defaultValue = "cinema") String categories) throws Exception {
        System.out.println(categories);
        if (getAllEventsServices != null)
            getAllEventsServices.getAllEventsKudaGo(categories);
        else {
            System.out.println("Something Wrong");
        }
        return "events";
    }

}
