package bacon.thepaul_t.wurmmod.configurablemobs;

import com.wurmonline.server.creatures.AttackIdentifier;
import com.wurmonline.server.creatures.AttackValues;

public class custommobAttack {
	boolean isPrimary = true;
	String attackIdentifier;
	String name;
	AttackValues attackValues;

	public AttackIdentifier attackIdentifier()
	{
		AttackIdentifier retVal = AttackIdentifier.STRIKE;
		switch(this.attackIdentifier.toLowerCase())
		{
			case "bite":
				retVal = AttackIdentifier.BITE;
				break;
			case "kick":
				retVal = AttackIdentifier.KICK;
				break;
			case "headbutt":
				retVal = AttackIdentifier.HEADBUTT;
				break;
			default:
				retVal = AttackIdentifier.STRIKE;
				break;
		}
		return retVal;
	}
}
