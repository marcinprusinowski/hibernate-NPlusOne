package com.example.inheritancehibernate.service;

import com.example.inheritancehibernate.model.Node;
import com.example.inheritancehibernate.model.SubNode;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface NodeService {

    Node add(Node node) throws IllegalArgumentException;
    Node addSubNode(long parentId, SubNode subNode) throws IllegalArgumentException;
    Node get(long id) throws NoSuchElementException;
    Collection<Node> getAll();
}
