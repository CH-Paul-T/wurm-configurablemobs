package bacon.thepaul_t.wurmmod.configurablemobs;

import com.wurmonline.server.creatures.AttackAction;
import com.wurmonline.server.creatures.AttackValues;
import com.wurmonline.server.creatures.CreatureTypes;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modsupport.CreatureTemplateBuilder;
import org.gotti.wurmunlimited.modsupport.creatures.EncounterBuilder;
import org.gotti.wurmunlimited.modsupport.creatures.ModCreature;
import bacon.thepaul_t.wurmmod.configurablemobs.custommobTemplate;

public class custommob implements ModCreature, CreatureTypes {
	private Logger logger = Logger.getLogger(this.getClass().getName());
    private int templateId;
    private custommobTemplate mobData;
    public custommob(custommobTemplate mob) {
    	 mobData = mob;
    }

    public CreatureTemplateBuilder createCreateTemplateBuilder() {
    	CreatureTemplateBuilder builder = new CreatureTemplateBuilder(mobData.identifier, mobData.name, mobData.description, mobData.model, mobData.types, mobData.bodyType, mobData.vision, mobData.sex, mobData.centimetersHigh, mobData.centimetersLong, mobData.centimetersWide, mobData.deathSndMale, mobData.deathSndFemale, mobData.hitSndMale, mobData.hitSndFemale, mobData.naturalArmour, mobData.handDam, mobData.kickDam, mobData.biteDam, mobData.headDam, mobData.breathDam, mobData.speed, mobData.moveRate, mobData.itemsDropped, mobData.maxHuntDist, mobData.aggress, mobData.meatMaterial);
        this.templateId = builder.getTemplateId();
        logger.log(Level.INFO, "Adding '"+mobData.identifier+"' as template id '"+this.templateId+"'.");
        for(int i=0; i < mobData.skills.length; i++)
        {
        	logger.log(Level.INFO, "Adding skill '"+mobData.skills[i][0]+"' to '"+mobData.identifier+"' ("+this.templateId+").");
        	builder.skill(mobData.skills[i][0], (float) mobData.skills[i][1]);
        }
        builder.boundsValues(mobData.boundsBox[0],mobData.boundsBox[1],mobData.boundsBox[2],mobData.boundsBox[3]);
        builder.handDamString(mobData.handAttack);
        builder.maxAge(mobData.maxAge);
        builder.armourType(mobData.armourType);
        builder.baseCombatRating(mobData.combatRating);
        builder.combatDamageType(mobData.damageType);
        builder.maxGroupAttackSize(4);
        builder.maxPercentOfCreatures(mobData.maxPerc);
        builder.sizeModifier(mobData.sizeModifier[0], mobData.sizeModifier[1], mobData.sizeModifier[2]);
        builder.usesNewAttacks(true);
        builder.alignment(mobData.alignment);
        builder.denMaterial(mobData.denMaterial);
        builder.denName(mobData.denName);
        builder.glowing(mobData.glowing);
        for(int i=0; i < mobData.attacks.length; i++)
        {
        	custommobAttack attack = mobData.attacks[i];
        	if(attack.isPrimary)
        	{
        		logger.log(Level.INFO, "Adding primary attack '"+attack.name+"' to '"+mobData.identifier+"' ("+this.templateId+").");
        		builder.addPrimaryAttack(new AttackAction(attack.name, attack.attackIdentifier(), new AttackValues(7.0F, 0.01F, 6.0F, 3, 1, (byte) 0, false, 2, 1.0F)));
        	} else {
        		logger.log(Level.INFO, "Adding secondary attack '"+attack.name+"' to '"+mobData.identifier+"' ("+this.templateId+").");
        		builder.addSecondaryAttack(new AttackAction(attack.name, attack.attackIdentifier(), new AttackValues(10.0F, 0.05F, 6.0F, 2, 1, (byte) 3, false, 3, 1.1F)));
        	}
        }
        return builder;
    }
    public void addEncounters() {
        if(this.templateId != 0) {
        	for(int i=0; i < mobData.encounters.length; i++)
        	{
        		custommobEncounter encounter = mobData.encounters[i];
        		logger.log(Level.INFO, "Adding encounter tile '"+encounter.tileType+"' to '"+mobData.identifier+"' ("+this.templateId+").");
        		(new EncounterBuilder(encounter.tileType)).addCreatures(this.templateId, encounter.spawnCount).build(encounter.spawnChance);
        	}
        }
    }
}
