/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.portalthreekingdoms;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class OverwhelmingForces extends CardImpl {

    public OverwhelmingForces(UUID ownerId) {
        super(ownerId, 79, "Overwhelming Forces", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{6}{B}{B}");
        this.expansionSetCode = "PTK";

        // Destroy all creatures target opponent controls. Draw a card for each creature destroyed this way.
        this.getSpellAbility().addEffect(new OverwhelmingForcesEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public OverwhelmingForces(final OverwhelmingForces card) {
        super(card);
    }

    @Override
    public OverwhelmingForces copy() {
        return new OverwhelmingForces(this);
    }
}

class OverwhelmingForcesEffect extends OneShotEffect {

    public OverwhelmingForcesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures target opponent controls. Draw a card for each creature destroyed this way";
    }

    public OverwhelmingForcesEffect(final OverwhelmingForcesEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingForcesEffect copy() {
        return new OverwhelmingForcesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && getTargetPointer().getFirst(game, source) != null) {
            int destroyedCreature = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), getTargetPointer().getFirst(game, source), game)) {
                if (permanent.destroy(source.getSourceId(), game, false)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                new DrawCardSourceControllerEffect(destroyedCreature).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
