package server;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import shared.Objednavka;

public class Uloziste extends UnicastRemoteObject implements shared.Uloziste {

	HashMap<Integer, Objednavka> seznam = new HashMap<Integer, Objednavka>();

	protected Uloziste() throws RemoteException {
		super();
		Timer timer = new Timer();
		TimerTask hourlyTask = new TimerTask() {
			@Override
			public void run() {
				for (Objednavka objednavka : seznam.values()) {

					LocalDateTime ldt = LocalDateTime.now();
					ldt = ldt.minusMinutes(10);

					try {
						if (objednavka.getCasObjednavky().compareTo(ldt) < 1) {
							seznam.remove(objednavka.getId());
							System.out.println("Smazana objednavka " + objednavka.getId());
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}

				}
			}
		};

		// schedule the task to run starting now and then every hour...
		timer.schedule(hourlyTask, 0l, 1000 * 60 * 60);

	}

	static Uloziste instance = null;

	@Override
	public int getPocet() throws RemoteException {
		return this.seznam.size();
	}

	@Override
	public Objednavka getObjednavka(int index) throws RemoteException {
		return seznam.get(index);
	}

	public int counter = 0;

	@Override
	public void odeber(int id) throws RemoteException {
		seznam.remove(id);
	}

	public static Uloziste getInstance() throws RemoteException {
		if (instance == null) {
			instance = new Uloziste();
		}
		return instance;
	}

	@Override
	public void pridej(Objednavka objednavka) throws RemoteException {
		seznam.put((Integer) objednavka.getId(), objednavka);

	}

}