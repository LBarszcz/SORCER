package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.Scheduler;
import edu.pjatk.inn.coffeemaker.impl.Order;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.core.provider.rendezvous.ServiceJobber;
import sorcer.po.operator;
import sorcer.service.*;
import sorcer.service.Domain;

import static edu.pjatk.inn.coffeemaker.impl.Recipe.getRecipe;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sorcer.co.operator.*;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.result;
import static sorcer.mo.operator.*;
import static sorcer.mo.operator.result;
import static sorcer.po.operator.ent;
import static sorcer.po.operator.invoker;
import static sorcer.so.operator.*;

import java.util.Date;

@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class SchedulerTest {
	private final static Logger logger = LoggerFactory.getLogger(SchedulerTest.class);

	private Context order1, order2, order3;

	@Before
	public void setUp() throws ContextException {

		Recipe espresso = new Recipe();
		espresso.setName("espresso");
		espresso.setPrice(50);
		espresso.setAmtCoffee(6);
		espresso.setAmtMilk(1);
		espresso.setAmtSugar(1);
		espresso.setAmtChocolate(0);

		order1 = context(ent("name", "Mateusz"), ent("date", new Date()),
				ent("recipe", espresso));

		order2 = context(ent("name", "Łukasz"), ent("date", new Date()),
				ent("recipe", espresso));

		order3 = context(ent("name", "Paweł"), ent("date", new Date()),
				ent("recipe", espresso));

	}

	@After
	public void cleanUp() throws Exception {
	}

	@Test
	public void testAddOrder() throws Exception {
		Exertion cmt = task(sig("addOrder", Scheduler.class), order1);
		Context out = context(exert(cmt));
		assertEquals(value(out, "addOrder"), true);
	}
}

