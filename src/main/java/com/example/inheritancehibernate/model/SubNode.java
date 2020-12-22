package com.example.inheritancehibernate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_node_id" )
    private Long id ;
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(
            name = "parent_node" ,
            referencedColumnName = "node_id" ,
            foreignKey = @ForeignKey(name = "fk_parent_node")
    )
    private Node parentNode ;

    public SubNode(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubNode subNode = (SubNode) o;
        return Objects.equals(id, subNode.id) &&
                Objects.equals(name, subNode.name) &&
                Objects.equals(parentNode.getId(), subNode.parentNode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentNode);
    }
}
