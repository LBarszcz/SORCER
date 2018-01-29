package edu.pjatk.inn.coffeemaker;

import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;

public interface CoffeeService {

    public Context addOrder(Context context) throws RemoteException, ContextException;

    public Context realizeOrder(Context context) throws RemoteException, ContextException;

}