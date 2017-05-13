package videopoker.game;

/**
 * Game interface designed as a state machine for a
 * videopoker round. Each method deals with a different stage
 * of the game and executes that stage's corresponding actions.
 *
 * The state machine enforces a logic sequence for each game round,
 * only changing states when certain stage specific criteria has been met.
 *
 * Each stage may allow for different commands to be used like showing advices,
 * player statistics or others.
 */
public interface Game{

    /**
     * Execute all bet related game actions like bet amount
     * verification or players credir availability.
     */
    public void betStage();

    /**
     * Execute all deal related actions like shuffling the deck
     * and drawing a new hand for the player.
     */
    public void dealStage();

    /**
     * Execute all hold related actions like letting the player
     * choose which cards to hold and advising about the best play to make.
     */
    public void holdStage();

    /**
     * Execute final round actions like evaluating players hand, updating it's game
     * statistics and handling the player's payment.
     */
    public void evaluationStage();
}
