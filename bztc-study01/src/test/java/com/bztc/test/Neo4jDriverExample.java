package com.bztc.test;

import org.neo4j.driver.Record;
import org.neo4j.driver.*;

/**
 * @author daism
 * @create 2024-01-23 17:16
 * @description Neo4jDriverExample
 */
public class Neo4jDriverExample {
    public static void main(String[] args) {
        // 连接到Neo4j数据库
        Driver driver = GraphDatabase.driver("bolt://10.211.55.15:7687", AuthTokens.basic("neo4j", "12345678"));

        // 创建节点
        try (Session session = driver.session()) {
            session.run("CREATE (n:Person {name: 'Alice'})");
            session.run("CREATE (n:Person {name: 'Bob'})");
        }

        // 创建关系
        try (Session session = driver.session()) {
            session.run("MATCH (a:Person), (b:Person) WHERE a.name = 'Alice' AND b.name = 'Bob' CREATE (a)-[:FRIENDS]->(b)");
        }

        // 查询数据
        try (Session session = driver.session()) {
            Result result = session.run("MATCH (n:Person) RETURN n.name AS name");
            while (result.hasNext()) {
                Record record = result.next();
                String name = record.get("name").asString();
                System.out.println(name);
            }
        }

        // 关闭连接
        driver.close();
    }
}
