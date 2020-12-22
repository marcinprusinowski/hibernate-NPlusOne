package com.example.inheritancehibernate.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parentNode", cascade = {CascadeType.PERSIST} )
    @Fetch(FetchMode.JOIN)
    @Column(name = "parent_node")
    private Set<SubNode> nodes = new HashSet<>();

    public Node(String name) {
        this.name = name;
    }

    public void addSubNode(SubNode subNode) {
        getNodes().add(subNode);
        subNode.setParentNode(this);
    }

}
