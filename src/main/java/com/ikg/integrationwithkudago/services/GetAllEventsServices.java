package com.ikg.integrationwithkudago.services;

import com.ikg.integrationwithkudago.camelroutes.GetAllEventsKudaGoRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
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

    public void getAllEventsKudaGo(String categories) throws Exception {
        StringBuilder constructorGet = new StringBuilder("lang=&fields=&expand=&order_by=&text_format=&ids=&location=&actual_since=" +
            "1444385206&actual_until=&is_free=&categories=");
        if (!categories.isEmpty()) constructorGet.append(categories).append("&lon=&lat=&radius=");
        else constructorGet.append("&lon=&lat=&radius=");
        context.addRoutes(new GetAllEventsKudaGoRoute());
        template.sendBodyAndHeader("direct:getAllEventsKudago", "New request", Exchange.HTTP_QUERY,
            constructorGet);

    }
}
