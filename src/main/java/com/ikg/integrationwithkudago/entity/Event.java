package com.ikg.integrationwithkudago.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Iurochkin
 * @link https://github.com/SilverRid
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private int id;
    private String title;
    private String slug;
}
