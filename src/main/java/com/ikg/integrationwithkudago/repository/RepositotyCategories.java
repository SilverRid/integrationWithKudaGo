package com.ikg.integrationwithkudago.repository;

import com.ikg.integrationwithkudago.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sergei Iurochkin
 * @link https://github.com/SilverRid
 */
public interface RepositotyCategories extends JpaRepository<Category, Integer> {
}
