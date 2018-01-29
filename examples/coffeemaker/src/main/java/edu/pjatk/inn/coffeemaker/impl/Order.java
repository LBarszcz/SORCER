package edu.pjatk.inn.coffeemaker.impl;

import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;

public class Order implements Serializable {
    private String name;
    private Date date;
    private Recipe recipe;
    
	public Order(String name, Date date, Recipe recipe) {
    	this.name = name;
    	this.date = date;
    	this.recipe = recipe;
    }

    public Order() {
    }

    public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}
    public void setDate(Date date) {
		this.date = date;
	}

	public Recipe getRecipe() {
		return recipe;
	}
    public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	static public Order getOrder(Context context) throws ContextException {
		Order o = new Order();
		try {
			o.name = (String)context.getValue("name");
			o.date = (Date)context.getValue("date");
			o.recipe = (Recipe)context.getValue("recipe");
		} catch (RemoteException e) {
			throw new ContextException(e);
		}
		return o;
	}

	static public Context getContext(Order order) throws ContextException {
		Context cxt = new ServiceContext();
		cxt.putValue("name", order.getName());
		cxt.putValue("date", order.getDate());
		cxt.putValue("recipe", order.getRecipe());
		return cxt;
	}
}