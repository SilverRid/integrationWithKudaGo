package com.ikg.integrationwithkudago.camelroutes;

import java.util.List;
import static org.springframework.http.HttpMethod.GET;
import com.ikg.integrationwithkudago.entity.Category;
import com.ikg.integrationwithkudago.repository.RepositotyCategories;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Sergei Iurochkin
 * @link https://github.com/SilverRid
 */
@Component
public class GetAllCategoriesKudaGoRoute extends RouteBuilder {
    @Autowired
    RepositotyCategories repositotyCategories;
    private final String REQUEST = "kudago.com/public-api/v1.4/event-categories/";
    @Override
    public void configure() throws Exception {

        from("direct:getAllCategoriesKudaGoRoute")
            .setHeader(Exchange.HTTP_METHOD).constant(GET)
            .routeId("getAllCategoriesKudaGoRoute")
            .log("Http Route started: \n ${headers}\n ${body} \n" + REQUEST)
            .to("direct:countinueGetAllCategoriesKudaGoRoute")
            .log("HTTP OK");

        from("direct:countinueGetAllCategoriesKudaGoRoute")
            .routeId("CONTINUE >>> GetAllCategoriesKudaGoRoute")
            .log("HTTP_QUERY >>>>>>>>>> \n ${headers}\n ")
            .to("https://" + REQUEST + "?throwExceptionOnFailure=false")
            .log("After GET REQUEST ${body} ${headers}")
            .choice()
            .when(exchange -> exchange.getMessage().getHeader("CamelHttpResponseCode").equals(HttpStatus.OK.value()))
            .unmarshal(new ListJacksonDataFormat(Category.class))
            .log(LoggingLevel.INFO, "Response :  \n ${headers}\n ${body} \n")
            .process(this::saveToDataBase)
            .log("GetAllCategoriesKudaGoRoute: >>>> After GET PROCESS ${body} ${headers}")
            .log("GetAllCategoriesKudaGoRoute: >>>> ATFTER 1 WHEN OK")
            .otherwise()
            .log(LoggingLevel.INFO, "GetAllCategoriesKudaGoRoute: >>>> AFTER RESPONCE BAD :  \n ${headers}\n ${body} \n")
            .log("GetAllCategoriesKudaGoRoute: >>>> CONTINUE OK ");

    }

    private void saveToDataBase(final Exchange exchange) {
        List<Category> categories = (List<Category>) exchange.getIn().getBody();
        repositotyCategories.saveAll(categories);
    }
}
