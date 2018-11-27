package com.bpc;

import java.sql.*;
import javax.swing.table.*;
import java.util.Vector;


/**
Cette classe permet d'afficher des données dans une JTable
en créant d'abord un modéle abstrait de données
*/
public class ResultsModel extends AbstractTableModel
{
   String[] columnNames = new String[0]; 
   Vector dataRows;              // vecteur vide, de lignes 
    
	
  ResultsModel()
  { 
  	super();
  	refresh();
  }
  
  /**
  Cette méthode effectue plusieurs taches :
   - identification du nombre et du titre des colonnes de la table
   - détermination du nombre de lignes de la table
   - extraire les tuples 
  @param rs Objet de type ResultSet
  */
  public void setResultSet(ResultSet rs)
  {	
    try
    { 
	ResultSetMetaData metadata = rs.getMetaData();
    int columns =  metadata.getColumnCount();    // nb de cols
  	columnNames = new String[columns]; 
	
	// nom cols
      for(int i = 0; i < columns; i++)
        columnNames[i] = metadata.getColumnLabel(i+1);

      // "prendre" toutes les lignes
      dataRows = new Vector();                     // Nouveau vecteur pour données
      String[] rowData; 	 
	                                               // stocker 1 ligne
      while(rs.next())                             // pour chaque ligne...
      {
        rowData = new String[columns];             // création d'un tableau pour les données
        for(int i = 0; i < columns; i++)           // pour chaque colonne...
          rowData[i] = rs.getString(i+1);          // "retire" la donnée
 
        dataRows.addElement(rowData);              // stocker la ligne dans un vecteur
      }

    }
    catch (SQLException sqle)
    {
      System.err.println(sqle);
    }
  }
  
  public void refresh()
  {
   fireTableDataChanged();
   fireTableChanged(null);
   }
  
 
  public int getColumnCount()
  {
    return columnNames.length; 
  }

  public int getRowCount()
  {
    if(dataRows == null)
      return 0;
    else
      return dataRows.size();
  }

  public Object getValueAt(int row, int column)
  {
    return ((String[])(dataRows.elementAt(row)))[column]; 
  }

  public String getColumnName(int column)
  {
    return columnNames[column] == null ? "Sans nom" : columnNames[column];
  }
}