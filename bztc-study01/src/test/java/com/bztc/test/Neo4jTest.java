package com.bztc.test;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Query;

import static org.neo4j.driver.Values.parameters;

/**
 * @author daism
 * @create 2024-01-23 16:52
 * @description neo4j测试
 */
public class Neo4jTest implements AutoCloseable {
    private final Driver driver;

    public Neo4jTest(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public static void main(String... args) {
        try (Neo4jTest greeter = new Neo4jTest("bolt://10.211.55.15:7687", "neo4j", "12345678")) {
            greeter.printGreeting("hello, world");
        }
    }

    @Override
    public void close() throws RuntimeException {
        driver.close();
    }

    public void printGreeting(final String message) {
        try (org.neo4j.driver.Session session = driver.session()) {
            String greeting = session.executeWrite(tx -> {
                Query query = new Query("CREATE (a:Greeting) SET a.message = $message RETURN a.message + ', from node ' + id(a)", parameters("message", message));
                org.neo4j.driver.Result result = tx.run(query);
                return result.single().get(0).asString();
            });
            System.out.println(greeting);
        }
    }
}
