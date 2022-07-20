package com.ikg.integrationwithkudago.services;

import com.ikg.integrationwithkudago.repository.RepositotyCategories;
import com.ikg.integrationwithkudago.testdb.MyDb;
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
    RepositotyCategories repositotyCategories;

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
        if (repositotyCategories.count() == 0) {
            template.sendBody("direct:getAllCategoriesKudaGoRoute", "Get ALL Categories");
        }

    }

}
