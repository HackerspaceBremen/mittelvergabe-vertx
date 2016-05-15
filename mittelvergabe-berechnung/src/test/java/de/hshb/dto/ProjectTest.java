package de.hshb.dto;

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
	public void testName() {
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

}
