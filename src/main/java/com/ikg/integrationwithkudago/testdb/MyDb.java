package com.ikg.integrationwithkudago.testdb;

import java.util.ArrayList;
import java.util.List;
import com.ikg.integrationwithkudago.entity.AllEvents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Iurochkin
 * @link https://github.com/SilverRid
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyDb {
    private List<AllEvents> allEvents = new ArrayList<>();
}
