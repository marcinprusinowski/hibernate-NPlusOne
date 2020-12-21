# HIBERNATE N + 1 PROBLEM 
What is the N + 1 problem?
This problem occurs when we want to fetch data associated with the One-To-Many relationship.
Hibernate then executes N + 1 queries to Database where N is the number of entities which are with a relationship with parent entity which is selected by 1 extra query. 
Simply said, hibernate first executes the query to select all parent entities and then for each of the children. Let's say we have 100 parent entities and each of them has 100 children. If we want to fetch all parents with associated children, then we have 10 000 entities to select. Incorrect implementation can cause performance problems.

## How to detect N + 1 problem?
Actually, it is not that hard as it seems to be.
All we need to do is enable one property in our configuration file
```
spring.jpa.show-sql=true
```
When we run our findAll method we can see all the queries executed by hibernate.
![](https://i.ibb.co/t3mdVtr/Screenshot-2020-12-21-at-16-53-15.png)
Pretty crazy right?

## How to solve it?

First of all, we need to compare FetchMode and FetchType
FetchType is a way to tell hibernate how to fetch related objects when a call comes from code. Let's say we have a One-To-Many relationship between parent and child. We can fetch the child object on Many side in two ways.
- Lazy -> Child is loaded when it's needed. In a real example, it usually happens when we use the Getter method on a parent object.
- Eager -> Child is loaded when the parent is loaded.

FetchMode is a parameter that tells hibernate how to fetch associated entities from the database.
- SELECT - Load relations lazy.  Every connected entity is loaded with another select query. This is set as the default
- SUBSELECT - As the name suggests the SubSelect mode will make a subselect query. So if we query a parent entity, hibernate will execute two queries. One for the parent and second for all children.
- JOIN - Load relations Eager. So every connected entity is loaded with one query which joins tables together. 

### Solution
All we have to do is choose the right FetchMode using @Fetch annotation.
```
    @OneToMany(mappedBy = "parentNode", cascade = {CascadeType.PERSIST} )
    @Fetch(FetchMode.JOIN)
    @Column(name = "parent_node")
    private Set<SubNode> nodes = new HashSet<>();
```
There is a tricky part. We have to override the method in our repository using @Query annotation because Spring Data use Criteria API to generate JPQL queries and then the fetch mode is ignored. 
```
   @Query("""
            select n from Node n left join fetch n.nodes 
            """)
    @Override
    Iterable<Node> findAll();
```
As you can see, now we need only one query to get all the data instead of N + 1.
```
select 
    node0_.node_id as node_id1_0_0_,
    node0_.name as name2_0_0_, nodes1_.parent_node as parent_n3_1_1_,
    nodes1_.sub_node_id as sub_node1_1_1_,
    nodes1_.sub_node_id as sub_node1_1_2_,
    nodes1_.name as name2_1_2_,
    nodes1_.parent_node as parent_n3_1_2_ 
from node node0_ left outer join sub_node nodes1_ 
on node0_.node_id=nodes1_.parent_node where node0_.node_id=?

```
