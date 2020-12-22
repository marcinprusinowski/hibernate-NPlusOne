package com.example.inheritancehibernate.service;


import com.example.inheritancehibernate.model.Node;
import com.example.inheritancehibernate.repo.NodeRepo;
import com.example.inheritancehibernate.model.SubNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class CrudNodeService implements NodeService {


    private final NodeRepo repo;

    @Autowired
    public CrudNodeService(NodeRepo repo) {
        this.repo = repo;
    }

    @Override
    public Node add(Node node){
        return repo.save(node);
    }

    @Override
    @Transactional
    public Node addSubNode(long parentId , SubNode subNode) throws IllegalArgumentException {
        Node parent = repo.findById(parentId).orElseThrow( () -> new IllegalArgumentException("Parent does not exits"));
        parent.addSubNode(subNode);
        return parent;
    }

    @Override
    public Node get(long id){
        return repo.findById(id).orElseThrow(()-> new NoSuchElementException("Node does not exists"));
    }

    @Override
    public Collection<Node> getAll() {
        return (Collection<Node>) repo.findAll();
    }
}


