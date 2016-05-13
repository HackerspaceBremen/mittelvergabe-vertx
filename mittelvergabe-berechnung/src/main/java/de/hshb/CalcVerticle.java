package de.hshb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hshb.dto.Project;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class CalcVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    this.vertx.eventBus().consumer("mittelvergabe.calc", message -> {
      final JsonObject response = calculate((JsonObject) message.body());

      message.reply(response);
    });
  }

  private JsonObject calculate(final JsonObject body) {
    final List<Project> projects = parseToProjects(body);
    final Double availableMoney = body.getDouble("availableMoney");

    // TODO calculate

    final JsonArray array = new JsonArray();
    projects.forEach(project -> array.add(project.toJson()));
    body.put("projects", array);
    return body;
  }

  private List<Project> parseToProjects(final JsonObject body) {
    final JsonArray jsonArray = body.getJsonArray("projects");

    final List<Project> projects = new ArrayList<>();
    jsonArray.stream().forEach(jsonObject -> projects.add(new Project((JsonObject) jsonObject)));

    Collections.sort(projects);
    return projects;
  }
}
