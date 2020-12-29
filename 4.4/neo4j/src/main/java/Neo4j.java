import org.neo4j.driver.*;

public class Neo4j {
    public static void main(String[] args){
        Driver driver = GraphDatabase.driver("bolt://localhost:11012", AuthTokens.basic("neo4j", "12345"));
        Session session = driver.session();
        session.run("LOAD CSV WITH HEADERS " +
                "FROM \"file:///generic-food.csv\" AS Row " +
                "MERGE (food:Food {food_name: Row.food_name}) " +
                "SET food.food_name=Row.food_name, food.scientific_name=Row.scientific_name " +
                "MERGE (group:Group {group_name: Row.group_name})");

        session.run("LOAD CSV WITH HEADERS " +
                "FROM \"file:///generic-food.csv\" AS Row " +
                "MATCH (food:Food {food_name: Row.food_name}),(group:Group {group_name:Row.group_name}) " +
                "CREATE (food)-[:BELONGS_TO_GROUP]->(group)");
        
        session.close();
        driver.close();
    }

    /*
    LOAD CSV WITH HEADERS
FROM "file:///generic-food.csv" AS Row
MERGE (food:Food {food_name: Row.food_name})
SET food.food_name=Row.food_name, food.scientific_name=Row.scientific_name
MERGE (group:Group {group_name: Row.group_name})

LOAD CSV WITH HEADERS
FROM "file:///git_selection.csv" AS Row
MATCH (user:User {svn_id: Row.svn_id}),(project:Project {project_name:Row.project_name})
CREATE (user)-[:COMMIT {num: Row.num, role_on_project: Row.role_on_project}]->(project)
     */

    public static void insertDataset(){

    }
}
