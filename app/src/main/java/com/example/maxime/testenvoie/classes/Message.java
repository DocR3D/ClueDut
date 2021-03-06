package com.example.maxime.testenvoie.classes;

public class Message {
	private int            index;   // numéro du joueur
	private String[]       items;   // commande + paramètres
	private Server.Command command; // commande
	private Player.State   state;   // état du joueur
	
	public Message(int index, String command, Player.State state) {
		this.index   = index;
		
		// décomposition des éléments de la commande		
		this.items = command.split(" +");

		this.state = state;

		try {
			this.command = checkCommand(this.items);
			System.out.println(this.command);
		}
		catch(Erreur e) {
			this.command = Server.Command.NULL;
		}
	}

	// examen de la commande
	private Server.Command checkCommand(String[] items) throws Erreur {
		if (items[0].compareToIgnoreCase("CONNECT") == 0)
			if (this.state == Player.State.DISCONNECTED)
				if (items.length == 2)
					return Server.Command.CONNECT;
		
		if (items[0].compareToIgnoreCase("COLOR") == 0)
			if (this.state == Player.State.CONNECTED)
				if (items.length == 2)
					return Server.Command.COLOR;

        if (items[0].compareToIgnoreCase("ACS") == 0) {
        		return Server.Command.ACS;
		}

		if (items[0].compareToIgnoreCase("MOVE") == 0) {
			return Server.Command.MOVE;
		}

        if (items[0].compareToIgnoreCase("HYP") == 0)
            return Server.Command.HYP;

		if (items[0].compareToIgnoreCase("NO") == 0)
				return Server.Command.NO;

		if (items[0].compareToIgnoreCase("YES") == 0)
				return Server.Command.YES;

		if (items[0].compareToIgnoreCase("START") == 0)
			if (this.state == Player.State.READY)
				if (items.length == 1)
					return Server.Command.START;

		if (items[0].compareToIgnoreCase("DICE") == 0)
			if (this.state == Player.State.READY)
				if (items.length == 1)
					return Server.Command.DICE;
		if (items[0].compareToIgnoreCase("INITJEU") == 0)
			if (this.state == Player.State.READY)
				if (items.length == 1)
					return Server.Command.INITJEU;

		if (items[0].compareToIgnoreCase("READY") == 0)
			if (this.state == Player.State.CONNECTED)
				if (items.length == 1)
					return Server.Command.READY;
				else


		if (items[0].compareToIgnoreCase("NOTREADY") == 0)
			if (this.state == Player.State.CONNECTED)
				if (items.length == 2)
					return Server.Command.NOTREADY;
				else
					throw new Erreur();
			else
				throw new Erreur();
		
		throw new Erreur();
	}

	public int getIndex() {
		return this.index;
	}
	
	public String[] getCommandItems() {
		return this.items;
	}

	public Server.Command getCommand() {
		return this.command;
	}
}

