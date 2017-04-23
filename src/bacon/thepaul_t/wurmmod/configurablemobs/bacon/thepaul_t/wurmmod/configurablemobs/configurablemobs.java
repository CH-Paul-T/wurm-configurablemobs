package bacon.thepaul_t.wurmmod.configurablemobs;

import java.util.logging.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmMod;
import org.gotti.wurmunlimited.modsupport.creatures.ModCreatures;
import com.google.gson.*;
import com.wurmonline.server.creatures.AttackValues;

public class configurablemobs implements WurmMod, Initable {
	private Logger logger = Logger.getLogger(this.getClass().getName());
    public configurablemobs() {
    }

    public void init() {
    	logger.log(Level.INFO, "Initialising ConfigurableMob mod...");
        Path _path = Paths.get("mobs/").toAbsolutePath();
        String path = _path.toString();
    	if(!Files.exists(_path))
    	{
    		this.createTemplateF();
    	}
        ModCreatures.init();
        logger.log(Level.INFO, "Loading mob templates from '"+path+"' directory...");
        File dir = new File(path);
        File[] dirContents = dir.listFiles();
        for(File dirFile : dirContents)
        {
        	String fname = dirFile.getName();
        	if(fname.length() <= 3) continue;
        	String fext = fname.substring(fname.length()-4, fname.length());
        	if(!fext.equals(".mob"))
        	{
        		logger.log(Level.WARNING, "Skipping '"+fname+"' as incorrect extension '"+fext+"'...");
        		continue;
        	}
        	logger.log(Level.INFO, "Loading '"+fname+"'...");
        	String mobJson;
        	try {
        		mobJson = readFile(dirFile.getAbsolutePath(), Charset.defaultCharset());
        	} catch(Exception e)
        	{
        		logger.log(Level.SEVERE, "Unable to read '"+fname+"'!", e);
        		continue;
        	}
        	try {
	        	Gson json = new Gson();
	        	custommobTemplate mob = json.fromJson(mobJson, custommobTemplate.class);
	            ModCreatures.addCreature(new custommob(mob));
        	} catch(Exception e)
        	{
        		logger.log(Level.SEVERE, "Invalid mob file format in '"+fname+"'... Skipping...", e);
        	}
        }
        logger.log(Level.INFO, "Finished initialising ConfigurableMob mod!");
    }
    void createTemplateF()
    {
    	logger.log(Level.INFO, "First run! Creating initial template file!");
    	try
    	{
    		//Make a template.mob file so people have an initial config...
	    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	custommobTemplate _blank = new custommobTemplate();
	    	_blank.types = new int[]{8, 14, 3, 12, 29, 43, 30, 32};
	    	_blank.itemsDropped = new int[]{8008,135};
	    	_blank.skills = new int[2][2];
	    	_blank.skills[0][0] = 1;
	    	_blank.skills[0][1] = 2;
	    	_blank.skills[1][0] = 3;
	    	_blank.skills[1][1] = 4;
	    	_blank.attacks = new custommobAttack[2];
	    	_blank.attacks[0] = new custommobAttack();
	    	_blank.attacks[0].isPrimary = true;
	    	_blank.attacks[0].name = "claw";
	    	_blank.attacks[0].attackIdentifier = "strike";
	    	_blank.attacks[0].attackValues = new AttackValues(7.0F, 0.01F, 6.0F, 3, 1, (byte) 0, false, 2, 1.0F);
	    	_blank.attacks[1] = new custommobAttack();
	    	_blank.attacks[1].isPrimary = false;
	    	_blank.attacks[1].name = "kick";
	    	_blank.attacks[1].attackIdentifier = "kick";
	    	_blank.attacks[1].attackValues = new AttackValues(10.0F, 0.05F, 6.0F, 2, 1, (byte) 3, false, 3, 1.1F);
	    	_blank.encounters = new custommobEncounter[2];
	    	_blank.encounters[0] = new custommobEncounter();
	    	_blank.encounters[1] = new custommobEncounter();
	    	String json = gson.toJson(_blank);
	    	(new File(Paths.get("mobs/").toAbsolutePath().toString())).mkdirs();
	    	PrintWriter writer = new PrintWriter(Paths.get("mobs/").toAbsolutePath().toString()+"/template.mob", "UTF-8");
	        writer.println(json);
	        writer.close();
    	} catch(Exception e)
    	{
    		logger.log(Level.SEVERE, "Unable to create directory and templates!", e);
    	}
    }
    static String readFile(String path, Charset encoding) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
}