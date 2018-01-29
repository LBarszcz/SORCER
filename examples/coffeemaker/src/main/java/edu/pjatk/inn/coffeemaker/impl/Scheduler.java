package edu.pjatk.inn.coffeemaker.impl;

import edu.pjatk.inn.coffeemaker.SchedulerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author   Sarah & Mike
 */
public class Scheduler implements SchedulerInterface {
	private final static Logger logger = LoggerFactory.getLogger(Scheduler.class);

	private ArrayList<Order> orders;

	public Scheduler() {
	    orders = new ArrayList<Order>();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	// Implementation of Scheduler
	@Override
	public Context addOrder(Context context) throws RemoteException, ContextException {
		Order o = Order.getOrder(context);
		orders.add(o);
		context.putValue("addOrder", true);
		return context;
	}

	@Override
	public Context realizeOrder(Context context) throws RemoteException, ContextException {
		Order o = null;
		if(orders.size() > 0){
			o = orders.remove(0);
		}

		context.putValue("realizeOrder", o);
		return context;
	}
}
