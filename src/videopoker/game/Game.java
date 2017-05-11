package videopoker.game;

public interface Game{

    /**
     * Execute all bet related game actions.
     */
    public void betStage();

    /**
     * First deal
     */
    public void dealStage();

    public void holdStage();

    public void evaluationStage();
}
