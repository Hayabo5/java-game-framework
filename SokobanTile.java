package sokoban;

/**
 * Represents all possible tile types in Sokoban.
 */
public enum SokobanTile {
    BLANK,           // empty space
    WALL,            // wall
    GOAL,            // target spot
    CRATE,           // box
    CRATE_ON_GOAL,   // box on target
    PLAYER,          // player
    PLAYER_ON_GOAL   // player standing on target
}