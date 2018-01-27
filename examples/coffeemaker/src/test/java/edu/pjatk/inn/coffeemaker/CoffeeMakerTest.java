package edu.pjatk.inn.coffeemaker;

import edu.pjatk.inn.coffeemaker.impl.CoffeeMaker;
import edu.pjatk.inn.coffeemaker.impl.Inventory;
import edu.pjatk.inn.coffeemaker.impl.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.service.ContextException;
import sorcer.service.Exertion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static sorcer.eo.operator.*;
import static sorcer.so.operator.eval;
/**
 * @author Mike Sobolewski
 */
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/coffeemaker")
public class CoffeeMakerTest {
	private final static Logger logger = LoggerFactory.getLogger(CoffeeMakerTest.class);

	private CoffeeMaker coffeeMaker;
	private Inventory inventory;
	private Recipe espresso, mocha, macchiato, americano;

	@Before
	public void setUp() throws ContextException {
		coffeeMaker = new CoffeeMaker();
		inventory = coffeeMaker.checkInventory();

		espresso = new Recipe();
		espresso.setName("espresso");
		espresso.setPrice(50);
		espresso.setAmtCoffee(6);
		espresso.setAmtMilk(1);
		espresso.setAmtSugar(1);
		espresso.setAmtChocolate(0);

		mocha = new Recipe();
		mocha.setName("mocha");
		mocha.setPrice(100);
		mocha.setAmtCoffee(8);
		mocha.setAmtMilk(1);
		mocha.setAmtSugar(1);
		mocha.setAmtChocolate(2);

		macchiato = new Recipe();
		macchiato.setName("macchiato");
		macchiato.setPrice(40);
		macchiato.setAmtCoffee(7);
		macchiato.setAmtMilk(1);
		macchiato.setAmtSugar(2);
		macchiato.setAmtChocolate(0);

		americano = new Recipe();
		americano.setName("americano");
		americano.setPrice(40);
		americano.setAmtCoffee(7);
		americano.setAmtMilk(1);
		americano.setAmtSugar(2);
		americano.setAmtChocolate(0);
	}

	@Test
	public void testAddRecipe() {
		assertTrue(coffeeMaker.addRecipe(espresso));
	}

	@Test
	public void testContextCofee() throws ContextException {
		assertTrue(espresso.getAmtCoffee() == 6);
	}

	@Test
	public void testContextMilk() throws ContextException {
		assertTrue(espresso.getAmtMilk() == 1);
	}

	@Test
	public void addRecepie() throws Exception {
		coffeeMaker.addRecipe(mocha);
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addContextRecepie() throws Exception {
		coffeeMaker.addRecipe(Recipe.getContext(mocha));
		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
	}

	@Test
	public void addServiceRecepie() throws Exception {
		Exertion cmt = task(sig("addRecipe", coffeeMaker),
						context(types(Recipe.class), args(espresso),
							result("recipe/added")));

		logger.info("isAdded: " + eval(cmt));
		assertEquals(coffeeMaker.getRecipeForName("espresso").getName(), "espresso");
	}

	@Test
	public void addRecipes() throws Exception {
		coffeeMaker.addRecipe(mocha);
		coffeeMaker.addRecipe(macchiato);
		coffeeMaker.addRecipe(americano);

		assertEquals(coffeeMaker.getRecipeForName("mocha").getName(), "mocha");
		assertEquals(coffeeMaker.getRecipeForName("macchiato").getName(), "macchiato");
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");
	}

	@Test
	public void makeCoffee() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertEquals(coffeeMaker.makeCoffee(espresso, 200), 150);
	}

	@Test
	public void deleteRecipe() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertTrue(coffeeMaker.deleteRecipe(espresso));
		assertNull(coffeeMaker.getRecipeForName("espresso"));
	}

	@Test
	public void editRecipe() throws Exception {
		coffeeMaker.addRecipe(espresso);
		assertEquals(coffeeMaker.getRecipeForName("espresso").getName(), "espresso");
		coffeeMaker.editRecipe(espresso, americano);
		assertNull(coffeeMaker.getRecipeForName("espresso"));
		assertEquals(coffeeMaker.getRecipeForName("americano").getName(), "americano");
	}

	@Test
	public void addInventory() throws Exception {
		int oldChocolate = inventory.getChocolate();
		int oldCoffee = inventory.getCoffee();
		int oldMilk = inventory.getMilk();
		int oldSugar = inventory.getSugar();
		
		int chocolate = 3;
		int coffee = 2;
		int milk = 8;
		int sugar = 7;

		assertTrue(coffeeMaker.addInventory(coffee, milk, sugar, chocolate));
		assertEquals(inventory.getChocolate(), oldChocolate + chocolate);
		assertEquals(inventory.getCoffee(), oldCoffee + coffee);
		assertEquals(inventory.getMilk(), oldMilk + milk);
		assertEquals(inventory.getSugar(), oldSugar + sugar);
	}

	@Test
	public void checkInventory() throws Exception {
		Inventory currentInventory = coffeeMaker.checkInventory();
		assertEquals(currentInventory.getChocolate(), 15);
		assertEquals(currentInventory.getCoffee(), 15);
		assertEquals(currentInventory.getMilk(), 15);
		assertEquals(currentInventory.getSugar(), 30);
	}

	@Test
	public void purchaseCoffee() throws Exception {
		coffeeMaker.addRecipe(espresso);
		Inventory beforePurchaseInventory = coffeeMaker.checkInventory();
		int amtCash = coffeeMaker.makeCoffee(espresso, 80);
		Inventory afterPurchaseInventory = coffeeMaker.checkInventory();

		assertEquals(beforePurchaseInventory.getChocolate() - espresso.getAmtChocolate(), afterPurchaseInventory.getChocolate());
		assertEquals(beforePurchaseInventory.getCoffee() - espresso.getAmtCoffee(), afterPurchaseInventory.getCoffee());
		assertEquals(beforePurchaseInventory.getMilk() - espresso.getAmtMilk(), afterPurchaseInventory.getMilk());
		assertEquals(beforePurchaseInventory.getSugar() - espresso.getAmtSugar(), afterPurchaseInventory.getSugar());
		assertEquals(amtCash, 30);
	}
}
