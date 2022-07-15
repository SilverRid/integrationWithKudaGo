package com.ikg.integrationwithkudago.controllers;

import com.ikg.integrationwithkudago.services.GetAllServicesKudaGO;
import com.ikg.integrationwithkudago.testdb.MyDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private GetAllServicesKudaGO getAllEventsServices;
    @Autowired
    private MyDb myDb;
    @GetMapping("/events")
    public String getAllEventsRequest(@RequestParam(defaultValue = "cinema") String categories,
                                      Model model) throws Exception {
        System.out.println(categories);
        if (getAllEventsServices != null)
            getAllEventsServices.getAllEventsKudaGo(categories);
        else {
            System.out.println("Something Wrong");
        }
        System.out.println("------------");
        System.out.println(myDb);
        System.out.println("------------");
        model.addAttribute(myDb.getAllEvents());
        System.out.println(model.asMap());
        return "events";
    }
    @GetMapping("/categories")
    public String getAllEventsRequest(Model model) throws Exception {

        if (getAllEventsServices != null)
            getAllEventsServices.getAllCategoriesKudaGo();
        else {
            System.out.println("Something Wrong with categories");
        }
      return "categories";
    }

}
