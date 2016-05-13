package de.hshb.dto;

import io.vertx.core.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project implements Comparable<Project>{

	private String name;

	private double moneyNeeded;

	private double moneyAlready;
	
	private double moneyAdded;

	private int votes;

	public Project(JsonObject json) {
		name = json.getString("name");
		moneyNeeded = json.getDouble("moneyNeeded");
		moneyAlready = json.getDouble("moneyAlready");
		votes = json.getInteger("votes");
		moneyAdded = 0.0;
	}
	
	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		json.put("name", name);
		json.put("moneyNeeded", moneyNeeded);
		json.put("moneyAlready", moneyAlready);
		json.put("votes", votes);
		json.put("moneyAdded", moneyAdded);
		return json;
	}

	@Override
	public int compareTo(Project compareProject) {
		int result;
		if(votes < compareProject.getVotes())
			result = -1;
		else if(votes>compareProject.getVotes())
			result = 1;
		else
			result = 0;
		
		return result;
	}
}
