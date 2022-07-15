package com.ikg.integrationwithkudago.services;

import com.ikg.integrationwithkudago.camelroutes.GetAllEventsKudaGoRoute;
import com.ikg.integrationwithkudago.entity.AllEvents;
import com.ikg.integrationwithkudago.testdb.MyDb;
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
public class GetAllServicesKudaGO implements GetAllServicesKudaGoIMPL{
    @Autowired
    ProducerTemplate template;

    @Autowired
    CamelContext context;

    @Autowired
    MyDb myDb;

    public void getAllEventsKudaGo(String categories) throws Exception {
        long currentTimeSec = System.currentTimeMillis() / 1000;
        StringBuilder constructorGet = new StringBuilder();

        constructorGet.append("lang=&fields=&expand=&order_by=&text_format=&ids=&location=msk&actual_since=")
            .append(currentTimeSec)
            .append("&actual_until=&is_free=&categories=");

        if (!categories.isEmpty()) {
            constructorGet.append(categories).append("&lon=&lat=&radius=");
        } else {
            constructorGet.append("&lon=&lat=&radius=");
        }
//        context.addRoutes(new GetAllEventsKudaGoRoute());
        template.sendBodyAndHeader("direct:getAllEventsKudago", "New request", Exchange.HTTP_QUERY,
            constructorGet);

    }

    @Override
    public void getAllCategoriesKudaGo() throws Exception {
        template.sendBody("direct:getAllCategoriesKudaGoRoute", "Get ALL Categories");
    }

}
