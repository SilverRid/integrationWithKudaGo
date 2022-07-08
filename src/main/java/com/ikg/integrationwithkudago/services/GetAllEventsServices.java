package com.ikg.integrationwithkudago.services;

import com.ikg.integrationwithkudago.camelroutes.GetAllEventsKudaGoRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*****

 @author Sergei Iurochkin
 @link https://github.com/SilverRid

 */
@Service
public class GetAllEventsServices {
    @Autowired
    ProducerTemplate template;
    @Autowired
    CamelContext context;

    public void getAllEventsKudaGo() throws Exception {

        context.addRoutes(new GetAllEventsKudaGoRoute());
        System.out.println(context.getRoutes());
        template.requestBody("direct:getAllEventsKudago", "New request");
    }
}
