package de.hshb.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import io.vertx.core.json.JsonObject;

public class ProjectTest implements WithAssertions {

	@Test
	public void testProject() {
		// @GIVEN
		// TODO change to random value
		Double moneyAlready = Double.valueOf(3.0);
		Double moneyNeeded = Double.valueOf(16.0);
		Integer votes = 5;
		String name = "CNC";

		JsonObject json = new JsonObject();
		json.put("moneyAlready", moneyAlready);
		json.put("moneyNeeded", moneyNeeded);
		json.put("name", name);
		json.put("votes", votes);

		// @WHEN
		Project project = new Project(json);

		// @THEN
		assertThat(project.getMoneyAlready()).isEqualTo(moneyAlready);
		assertThat(project.getMoneyNeeded()).isEqualTo(moneyNeeded);
		assertThat(project.getMoneyAdded()).isEqualTo(0.0);
		assertThat(project.getVotes()).isEqualTo(votes);
		assertThat(project.getName()).isEqualTo(name);
	}

	@Test
	public void testMissingJsonParameters() {
		// @GIVEN
		JsonObject json = new JsonObject();

		try {
			// @WHEN
			new Project(json);

			fail("IllegalArgumentException should have been thrown");
		} catch (Exception e) {
			// @THEN
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Test
	public void testToJson()  {
		// @GIVEN
		double moneyAdded = 4.0;
		double moneyAlready = 3.2;
		double moneyNeeded = 6.5;
		int votes = 13;

		String name = "Arduino WS";
		Project project = new Project();
		project.setMoneyAdded(moneyAdded);
		project.setMoneyAlready(moneyAlready);
		project.setMoneyNeeded(moneyNeeded);
		project.setName(name);
		project.setVotes(votes);
		
		// @WHEN
		JsonObject json = project.toJson();
		
		// @THEN
		assertThat(json.getString("name")).isEqualTo(name);
		assertThat(json.getDouble("moneyAdded")).isEqualTo(moneyAdded);
		assertThat(json.getDouble("moneyAlready")).isEqualTo(moneyAlready);
		assertThat(json.getDouble("moneyNeeded")).isEqualTo(moneyNeeded);
		assertThat(json.getInteger("votes")).isEqualTo(votes);
	}

	@Test
	public void testCompareToCaseSorting() throws Exception {
		// @GIVEN
		Project project5Votes = new Project();
		project5Votes.setVotes(5);
		Project project2Votes = new Project();
		project2Votes.setVotes(2);
		Project project3Votes = new Project();
		project3Votes.setVotes(3);
		List<Project> list = Arrays.asList(project3Votes, project2Votes,project5Votes);
	
		// @WHEN
		Collections.sort(list);
	
		// @THEN
		assertThat(list.get(0)).isEqualTo(project5Votes);
		assertThat(list.get(1)).isEqualTo(project3Votes);
		assertThat(list.get(2)).isEqualTo(project2Votes);
	}

	@Test
	public void testCompareToCaseSameAmountOfVotes() {
		// @GIVEN
		Project project5Votes = new Project();
		project5Votes.setVotes(5);
		Project project2Votes = new Project();
		project2Votes.setVotes(2);
		Project project2Votes2 = new Project();
		project2Votes2.setVotes(2);
		List<Project> list = Arrays.asList(project2Votes, project2Votes2,project5Votes);
	
		// @WHEN
		Collections.sort(list);
		
		// @THEN
		assertThat(list.get(0)).isEqualTo(project5Votes);
		assertThat(list.get(1)).isEqualTo(project2Votes);
		assertThat(list.get(2)).isEqualTo(project2Votes2);
	}

}
