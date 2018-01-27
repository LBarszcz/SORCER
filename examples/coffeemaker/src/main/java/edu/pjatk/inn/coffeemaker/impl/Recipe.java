package edu.pjatk.inn.coffeemaker.impl;

import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * @author   Sarah & Mike
 */
public class Recipe implements Serializable {
    private String name;
    private int price;
    private int amtCoffee;
    private int amtMilk;
    private int amtSugar;
    private int amtChocolate;
    
    public Recipe() {
    	this.name = "";
    	this.price = 0;
    	this.amtCoffee = 0;
    	this.amtMilk = 0;
    	this.amtSugar = 0;
    	this.amtChocolate = 0;
    }
    
    /**
	 * Returns the amount of chocolate in the coffee recipe.
	 * @return   Returns the amtChocolate.
	 */
    public int getAmtChocolate() {
		return amtChocolate;
	}
    
    /**
	 * Sets the amount of chocolate in the coffee recipe if the amount is positive number.
	 * @param amtChocolate   The amtChocolate to setValue.
	 */
    public void setAmtChocolate(int amtChocolate) {
		if (amtChocolate >= 0) {
			this.amtChocolate = amtChocolate;
		} 
	}
    
    /**
	 * Returns the amount of coffee in the coffee recipe.
	 * @return   Returns the amtCoffee.
	 */
    public int getAmtCoffee() {
		return amtCoffee;
	}
    
    /**
	 * Sets the amount of coffee in the coffee recipe if the amount is positive number.
	 * @param amtCoffee   The amtCoffee to setValue.
	 */
    public void setAmtCoffee(int amtCoffee) {
		if (amtCoffee >= 0) {
			this.amtCoffee = amtCoffee;
		} 
	}
    
    /**
	 * Returns the amount of milk in the coffee recipe.
	 * @return   Returns the amtMilk.
	 */
    public int getAmtMilk() {
		return amtMilk;
	}
    
    /**
	 *
	 * Sets the amount of milk in the coffee recipe if the amount is positive number.
	 * @param amtMilk   The amtMilk to setValue.
	 */
    public void setAmtMilk(int amtMilk) {
		if (amtMilk >= 0) {
			this.amtMilk = amtMilk;
		} 
	}
    
    /**
	 * Returns the amount of sugar in the coffee recipe.
	 * @return   Returns the amtSugar.
	 */
    public int getAmtSugar() {
		return amtSugar;
	}
    
    /**
	 * Sets the amount of sugar in the coffee recipe if the amount is positive number.
	 * @param amtSugar   The amtSugar to setValue.
	 */
    public void setAmtSugar(int amtSugar) {
		if (amtSugar >= 0) {
			this.amtSugar = amtSugar;
		} 
	}
    
    /**
	 * Returns the name of the coffee recipe.
	 * @return   Returns the name.
	 */
    public String getName() {
		return name;
	}
    
    /**
	 * Sets the name of the coffee recipe if the name isn't null.
	 * @param name   The name to setValue.
	 */
    public void setName(String name) {
    	if(name != null) {
    		this.name = name;
    	}
	}
    
    /**
	 * Returns the price of the coffee recipe.
	 * @return   Returns the price.
	 */
    public int getPrice() {
		return price;
	}
    
    /**
	 * Sets the price of the coffee recipe if the price is a positive number.
	 * @param price   The price to setValue.
	 */
    public void setPrice(int price) {
		if (price >= 0) {
			this.price = price;
		} 
	}

	/**
	 * Returns true if the recipe has the same name as given recipe.
	 * @param r  The recipe to compare.
	 * @return true if the recipes have the same name; false otherwise.
	 */ 
    public boolean equals(Recipe r) {
        if((this.name).equals(r.getName())) {
            return true;
        }
        return false;
    }

    /**
	 * Returns a String object representing this Recipe, equal to its name.
	 * @return a String representation of this Recipe.
	 */
    public String toString() {
    	return name;
    }

	/**
	 * Creates the recipe of the coffee from given Context and returns it.
	 * @param context  The context of the recipe.
	 * @return Returns the recipe.
	 */
	static public Recipe getRecipe(Context context) throws ContextException {
		Recipe r = new Recipe();
		try {
			r.name = (String)context.getValue("key");
			r.price = (int)context.getValue("price");
			r.amtCoffee = (int)context.getValue("amtCoffee");
			r.amtMilk = (int)context.getValue("amtMilk");
			r.amtSugar = (int)context.getValue("amtSugar");
			r.amtChocolate = (int)context.getValue("amtChocolate");
		} catch (RemoteException e) {
			throw new ContextException(e);
		}
		return r;
	}

	/**
	 * Creates the context of given recipe and returns it.
	 * @param recipe  The recipe to give.
	 * @return Returns the context of recipe.
	 */
	static public Context getContext(Recipe recipe) throws ContextException {
		Context cxt = new ServiceContext();
		cxt.putValue("key", recipe.getName());
		cxt.putValue("price", recipe.getPrice());
		cxt.putValue("amtCoffee", recipe.getAmtCoffee());
		cxt.putValue("amtMilk", recipe.getAmtMilk());
		cxt.putValue("amtSugar", recipe.getAmtSugar());
		cxt.putValue("amtChocolate", recipe.getAmtChocolate());
		return cxt;
	}
}