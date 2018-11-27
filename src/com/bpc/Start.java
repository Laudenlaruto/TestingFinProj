package com.bpc;

import javax.swing.*;

/**
*Gespernonnel.java - Point d'entrée du programme (contient le "main")
*@author Jacques André Augustin
*@version 1.0
*/

public class Start {

	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e)
		{
		}
		GraphicalUserInterface prog = new GraphicalUserInterface();		
		prog.lancer();
	}

}

