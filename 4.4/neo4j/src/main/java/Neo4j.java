import org.neo4j.driver.*;
// Author: Anthony dos Santos Pereira
public class Neo4j {
    public static void main(String[] args){
        Driver driver = GraphDatabase.driver("bolt://localhost:11012", AuthTokens.basic("neo4j", "12345"));
        Session session = driver.session();
        /* COMMENTED BECAUSE THE LOAD WORKED YAAAAAAY AND I DONT WANT TO ADD IT AGAIN SO YEAH NICE
        session.run("LOAD CSV WITH HEADERS " +
                "FROM \"file:///generic-food.csv\" AS Row " +
                "MERGE (food:Food {food_name: Row.food_name}) " +
                "SET food.food_name=Row.food_name, food.scientific_name=Row.scientific_name " +
                "MERGE (group:Group {group_name: Row.group_name})");

        session.run("LOAD CSV WITH HEADERS " +
                "FROM \"file:///generic-food.csv\" AS Row " +
                "MATCH (food:Food {food_name: Row.food_name}),(group:Group {group_name:Row.group_name}) " +
                "CREATE (food)-[:BELONGS_TO_GROUP]->(group)");

        */
        doAllQueries(session);
        // I understood that I dont need to print the results here, so if I run the programm and it finishes
        // with exit code 0 it means that everything work BECAUSE NO NEWS IS GOOD NEWS, so after that I'll
        // write the queries in a separate file and print all the results
        session.close();
        driver.close();
    }

    public static void doAllQueries(Session session){
        session.run("MATCH(food:Food) " +
                "RETURN food");
        session.run("MATCH(group:Group) " +
                "RETURN group");
        session.run("MATCH(group:Group) " +
                "RETURN group.group_name");
        session.run("MATCH(food:Food) " +
                "RETURN food.food_name");
        session.run("MATCH(food:Food) " +
                "RETURN food.scientific_name");
        session.run("MATCH (Food)-[r]->(Group) " +
                "WITH Food, count(r) as tot " +
                "RETURN Food, tot");
        session.run("MATCH(food:Food) " +
                "RETURN count(food)");
        session.run("MATCH(group:Group) " +
                "RETURN count(group)");
        session.run("MATCH(food:Food) " +
                "WHERE food.food_name CONTAINS 'Japanese'" +
                "RETURN count(food)");
        session.run("MATCH path=shortestPath( " +
                "(startingNode:Food)-[*..2]-(endingNode:Group))" +
                "WHERE startingNode.food_name CONTAINS 'Gr' and endingNode.group_name CONTAINS 'foods' " +
                "UNWIND nodes(path) as n " +
                "WITH startingNode, endingNode, COUNT(DISTINCT n) as dimension " +
                "RETURN startingNode, endingNode, dimension");
    }
}
