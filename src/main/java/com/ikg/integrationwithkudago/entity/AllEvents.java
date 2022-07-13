package com.ikg.integrationwithkudago.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Iurochkin
 * @link https://github.com/SilverRid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllEvents {
    private int count;
    private String next;
    private String previous;
    private List<Event> results;
}
