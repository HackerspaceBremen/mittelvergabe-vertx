package de.hshb;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.hshb.hss.reflect.Whitebox;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import io.vertx.core.Vertx;

public class CalcMainTest implements WithBDDMockito{

	private CalcMain calcMain;
	
	@Mock
	private Vertx vertxMock;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		calcMain = new CalcMain();
		
		Whitebox.setInternalState(calcMain, "vertx", vertxMock);
	}
	
	@Test
	public void testStart() throws Exception  {
	
		// @WHEN
		calcMain.start();
	
		// @THEN
		then(vertxMock).should().deployVerticle(CalcVerticle.class.getName());
	}

}
