package com.app.user;

import org.bson.Document;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MongoConnectionApplicationLiveTest {
  private static final String HOST = "localhost";
  private static final String PORT = "27017";
  private static final String DB = "userdb";
  private static final String USER = "admin";
  private static final String PASS = "root";

  private void assertInsertSucceeds(ConfigurableApplicationContext context) {
    String name = "A";

    MongoTemplate mongo = context.getBean(MongoTemplate.class);
    Document doc = Document.parse("{\"name\":\"" + name + "\"}");
    Document inserted = mongo.insert(doc, "items");

    assertNotNull(inserted.get("_id"));
    assertEquals(inserted.get("name"), name);
  }
}