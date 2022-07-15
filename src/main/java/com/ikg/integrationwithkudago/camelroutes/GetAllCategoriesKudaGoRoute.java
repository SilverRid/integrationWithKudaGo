package com.ikg.integrationwithkudago.camelroutes;

import static org.springframework.http.HttpMethod.GET;
import com.ikg.integrationwithkudago.testdb.MyDb;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
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
    MyDb myDb;
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
//            .unmarshal(new JacksonDataFormat(AllEvents.class))
//            .log(LoggingLevel.INFO, "Response :  \n ${headers}\n ${body} \n")
//            .process(this::checkRequeest)
            .log("GetAllCategoriesKudaGoRoute: >>>> After GET PROCESS ${body} ${headers}")
            .log("GetAllCategoriesKudaGoRoute: >>>> ATFTER 1 WHEN OK")
            .otherwise()
            .log(LoggingLevel.INFO, "GetAllCategoriesKudaGoRoute: >>>> AFTER RESPONCE BAD :  \n ${headers}\n ${body} \n")
            .log("GetAllCategoriesKudaGoRoute: >>>> CONTINUE OK ");

    }
}
