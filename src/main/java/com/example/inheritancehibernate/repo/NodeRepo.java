package com.example.inheritancehibernate.repo;


import com.example.inheritancehibernate.model.Node;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepo extends CrudRepository<Node, Long> {


    @Query("""
            select n from Node n left join fetch n.nodes 
            """)
    @Override
    Iterable<Node> findAll();
}
