package de.hshb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import de.hshb.dto.Project;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class CalcVerticle extends AbstractVerticle {

	private static Logger logger = LoggerFactory.getLogger(CalcVerticle.class);
	
  @Override
  public void start() throws Exception {
    this.vertx.eventBus().consumer("mittelvergabe.calc", message -> {
      final JsonObject response = calculate((JsonObject) message.body());

      message.reply(response);
    });
  }

  private JsonObject calculate(final JsonObject body) {
    final List<Project> projects = parseToProjects(body);
    final Double availableMoney = body.getDouble("moneyAvailable");

    sortAndCalculateProjects(availableMoney, projects);

    final JsonArray array = new JsonArray();
    projects.forEach(project -> array.add(project.toJson()));
    body.put("projects", array);
    return body;
  }

  private void sortAndCalculateProjects(Double availableMoney, List<Project> projects) {
	
	double money = availableMoney.doubleValue();
	boolean pssReached = false;
	Map<Integer, List<Project>> projectAmountMap = new HashMap<>();
	final Set<Integer> amountSet = new HashSet<>();
	for(int i=0; i<projects.size(); i++){
		Project project = projects.get(i);
		Integer amount = Integer.valueOf(project.getVotes());
		List<Project> projectList = projectAmountMap.get(amount);
		if(projectList == null){
			projectList = new ArrayList<>();
			projectAmountMap.put(amount, projectList);
		}
		projectList.add(project);
		amountSet.add(amount);
	}
	
	List<Integer> amountList = amountSet.stream().collect(Collectors.toList());
	for (int i = 0; i < amountList.size(); i++) {
		List<Project> projectList = projectAmountMap.get(amountList.get(0));
		if(pssReached){
			projectList.forEach(project -> {
				project.setMoneyAdded(0);
			});
		}
		//TODO-dragondagda: continue here 
	}
	
	for(int i=0; i<projects.size(); i++){
		Project project = projects.get(i);
		if(pssReached){
			project.setMoneyAdded(0);
		}else if("Sammelfach".equals(project.getName())){
			project.setMoneyAdded(money);
			money = 0;
			pssReached = true;
		}else if(project.getMoneyAlready()+money>=project.getMoneyNeeded()){
			double moneyAdded = project.getMoneyNeeded() - project.getMoneyAlready();
			project.setMoneyAdded(moneyAdded);
			money = money - moneyAdded;
		}else{
			project.setMoneyAdded(0.5*money);
			money *= 0.5;
		}
		
		logger.debug("name: " + project.getName());
		logger.debug("votes: " + project.getVotes());
		logger.debug("moneyAdded: " + project.getMoneyAdded());
		logger.debug("MONEY LEFT: " + money + "\n");
		
	}
}

private List<Project> parseToProjects(final JsonObject body) {
    final JsonArray jsonArray = body.getJsonArray("projects");

    final List<Project> projects = new ArrayList<>();
    jsonArray.stream().forEach(jsonObject -> projects.add(new Project((JsonObject) jsonObject)));

    Collections.sort(projects);
    return projects;
  }
}
