/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.darksteel;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author BursegSardaukar
 */
public class GoblinArchaeologist extends CardImpl {

    public GoblinArchaeologist(UUID ownerId) {
        super(ownerId, 63, "Goblin Archaeologist", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "DST";
        this.subtype.add("Goblin");
        this.subtype.add("Artificer");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}, {T]: Flip a coin. If you win the flip, destroy target artifact and untap Goblin Archaeologist. If you lose the flip, sacrifice Goblin Archaeologist.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinArchaeologistEffect(),new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(new FilterArtifactPermanent()));
        this.addAbility(ability);
    }

    public GoblinArchaeologist(final GoblinArchaeologist card) {
        super(card);
    }

    @Override
    public GoblinArchaeologist copy() {
        return new GoblinArchaeologist(this);
    }
}

class GoblinArchaeologistEffect extends OneShotEffect {

    public GoblinArchaeologistEffect() {
        super(Outcome.DestroyPermanent);
    }

    public GoblinArchaeologistEffect(final GoblinArchaeologistEffect ability) {
        super(ability);
    }

    @Override
    public GoblinArchaeologistEffect copy() {
        return new GoblinArchaeologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
       Player player = game.getPlayer(source.getControllerId());
       Permanent permanent = game.getPermanent(source.getSourceId());
       if (player != null && permanent != null) {
           if (!player.flipCoin(game)) {
               permanent.sacrifice(source.getSourceId(), game);
           }else{
               Permanent targetArtifact = game.getPermanent(source.getFirstTarget());
               targetArtifact.destroy(source.getSourceId(), game, true);
               permanent.untap(game);
           }
           return true;
       }
       return false;
   }
    
    @Override
    public String getText(Mode mode) {
        return "Flip a coin. If you win the flip, destroy target artifact and untap {this}. If you lose the flip, sacrifice {this}";
    }
}
