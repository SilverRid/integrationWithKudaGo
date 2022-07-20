package com.ikg.integrationwithkudago.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sergei Iurochkin
 * @link https://github.com/SilverRid
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "id", unique = true)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "categoriesIdSeq", sequenceName = "categories_id_seq", allocationSize = 1)
    private int id;
    @Column(name = "slug")
    private String slug;
    @Column(name = "name")
    private String name;
}
