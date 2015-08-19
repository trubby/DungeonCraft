package Trubby.co.th.IO;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Trubby.co.th.DG;

public class FileManager {

	DG plugin = DG.plugin;
	
	public FileManager(){
		File shFolder = new File(plugin.getDataFolder(), "Stronghold");
		if(!shFolder.exists()){
			shFolder.mkdirs();
		}
		
		//TODO 2 more folder
	}
	
	/**
	 *		FILE 
	 */
	public File dungeonCreateFile(String folderName,String fileName){
		File folder = new File(plugin.getDataFolder(), folderName);
		if(!folder.exists()){
			folder.mkdirs();
		}
		
		File f = new File(folder, fileName + ".yml");
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
		//Then read the file, and check the value
	}
	
	public File newFile(String name){
		File folder = new File(plugin.getDataFolder(), "config");
		File f = new File(folder,name + ".yml");
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return f;
		//Then read the file, and check the value
	}
	
	public FileConfiguration newConfig(String name){
		
		File f = newFile(name);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fc;
	}
	
}
