package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import model.PlayerData;
import model.Table;


public class IOController {

	private GameController gameController;

	/**
	 *  
	 */
	public IOController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * The method saves the current state of the application when the application is closed.
	 * @throws IOException when file cannot be saved
	 */
	public void save(File file) throws IOException {
		OutputStream outStream = null;
        ObjectOutputStream fileObjectOut = null;
		
        try{
        	outStream = new FileOutputStream(file);
            fileObjectOut = new ObjectOutputStream(outStream);
            fileObjectOut.writeObject(gameController.getTable());
        }catch(IOException io){
        	throw io;
        }finally{
        	if(outStream != null)
        		outStream.close();
        	if(fileObjectOut != null)
        		fileObjectOut.close();
        }            
	}

	public void load(File file) throws Exception {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		
		try
		{
			fileIn = new FileInputStream(file);
	        in = new ObjectInputStream(fileIn);
	        Table table = (Table) in.readObject();
	        gameController.setTable(table);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(fileIn != null)
				fileIn.close();
			
			if(in != null)
				in.close();
		}
	}
	

	/**
	 * Saves the current highscore entries to an xml file
	 * @param file the file to save the highscore to
	 * @throws Exception if the operation failed
	 */
	//TODO: Does this work with a list?
	public void saveHighscore(File file) throws Exception {
		OutputStream outStream = null;
        ObjectOutputStream fileObjectOut = null;
		
        try{
        	outStream = new FileOutputStream(file);
            fileObjectOut = new ObjectOutputStream(outStream);
            fileObjectOut.writeObject(gameController.getHighscoreController().getPlayerData());
        }catch(IOException io){
        	throw io;
        }finally{
        	if(outStream != null)
        		outStream.close();
        	if(fileObjectOut != null)
        		fileObjectOut.close();
        }            
	}

	/**
	 * Loads a highscore from an xml file
	 * @param file the file to load a highscore from
	 * @throws Exception if the file could not be loaded
	 */
	public void loadHighscore(File file) throws Exception {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		
		try
		{
			fileIn = new FileInputStream(file);
	        in = new ObjectInputStream(fileIn);
	        @SuppressWarnings("unchecked")
			ArrayList<PlayerData> highscore = (ArrayList<PlayerData>) in.readObject();
	        gameController.getHighscoreController().setPlayerData(highscore);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(fileIn != null)
				fileIn.close();
			
			if(in != null)
				in.close();
		}
	}
	

}
