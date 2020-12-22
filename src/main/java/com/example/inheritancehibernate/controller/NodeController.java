package com.example.inheritancehibernate.controller;


import com.example.inheritancehibernate.model.Node;
import com.example.inheritancehibernate.model.SubNode;
import com.example.inheritancehibernate.service.CrudNodeService;
import com.example.inheritancehibernate.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class NodeController {

    private final NodeService service;

    @Autowired
    public NodeController(CrudNodeService service) {
        this.service = service;
    }

    @PostMapping("/nodes")
    public Node add(@RequestBody Node node){
        return service.add(node);
    }
    @GetMapping("/nodes")
    public Collection<Node> getAll(){
        return service.getAll();
    }

    @GetMapping("/nodes/{nodeId}")
    public Node find(@PathVariable long nodeId){
        return service.get(nodeId);
    }

    @PostMapping("/nodes/{parentId}/subNodes")
    public Node add(@PathVariable long parentId,
                       @RequestBody SubNode node){
        return service.addSubNode(parentId , node);
    }

}
