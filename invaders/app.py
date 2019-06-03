"""
Primary module for Alien Invaders

This module contains the main controller class for the Alien Invaders application.

Rishab Kaul rk493
12/3/2018
"""
from consts import *
from game2d import *
from wave import *


class Invaders(GameApp):
    """
    The primary controller class for the Alien Invaders application

    This class extends GameApp and implements the various methods necessary for processing
    the player inputs and starting/running a game.

        Method start begins the application.

        Method update either changes the state or updates the Play object

        Method draw displays the Play object and any other elements on screen

    The primary purpose of this class is to manage the game state: which is when the
    game started, paused, completed, etc. It keeps track of that in an attribute
    called _state.

    INSTANCE ATTRIBUTES:
        view:   the game view, used in drawing (see examples from class)
                [instance of GView; it is inherited from GameApp]
        input:  the user input, used to control the ship and change state
                [instance of GInput; it is inherited from GameApp]
        _state: the current state of the game represented as a value from consts.py
                [one of STATE_INACTIVE, STATE_NEWWAVE, STATE_ACTIVE, STATE_PAUSED, STATE_CONTINUE, STATE_COMPLETE]
        _wave:  the subcontroller for a single wave, which manages the ships and aliens
                [Wave, or None if there is no wave currently active]
        _text:  the currently active message
                [GLabel, or None if there is no message to display]

    STATE SPECIFIC INVARIANTS:
        Attribute _wave is only None if _state is STATE_INACTIVE.
        Attribute _text is only None if _state is STATE_ACTIVE.

    ADDITIONAL ATTRIBUTES:
        _lastkeypress: whether key 'k' has been pressed already
        [True if key has been pressed, False otherwise]
        _numwaves: number of waves completed [int >= 0]
        _score: game score after a wave[int >= 0]
        _scoretext: Active Score message [Glabel, or None if there is no score to display]
    """


    # THREE MAIN GAMEAPP METHODS
    def start(self):
        """
        Initializes the application.

        This method is distinct from the built-in initializer __init__ (which you
        should not override or change). This method is called once the game is running.
        You should use it to initialize any game specific attributes.

        This method should make sure that all of the attributes satisfy the given
        invariants. When done, it sets the _state to STATE_INACTIVE and create a message
        (in attribute _text) saying that the user should press to play a game.
        """
        self._wave = None
        self._lastkeypress = False
        self._numwaves = 0
        self._state = STATE_INACTIVE
        self._score = 0
        if (self._state == STATE_INACTIVE):
            self._text = GLabel(
                text="Invaders"
                    "\n\n Press 'spacebar' to Shoot"
                    "\n Press 'A'/'D' to move left/right"
                    "\n Press 'k' to Play",
                font_size = 29,
                x = GAME_WIDTH / 2,
                y = GAME_HEIGHT / 2,
                font_name = 'RetroGame.ttf')
        else:
            self._text = None

    def update(self,dt):
        """
        Animates a single frame in the game.

        STATE_INACTIVE: This is the state when the application first opens.  It is a
        paused state, waiting for the player to start the game.  It displays a simple
        message on the screen. The application remains in this state so long as the
        player never presses a key.  In addition, this is the state the application
        returns to when the game is over (all lives are lost or all aliens are dead).

        STATE_NEWWAVE: This is the state creates a new wave and shows it on the screen.
        The application switches to this state if the state was STATE_INACTIVE in the
        previous frame, and the player pressed a key. This state only lasts one animation
        frame before switching to STATE_ACTIVE.

        STATE_ACTIVE: This is a session of normal gameplay.  The player can move the
        ship and fire laser bolts.  

        STATE_PAUSED: Like STATE_INACTIVE, this is a paused state. However, the game is
        still visible on the screen.

        STATE_CONTINUE: This state restores the ship after it was destroyed. The
        application switches to this state if the state was STATE_PAUSED in the
        previous frame, and the player pressed a key. This state only lasts one animation
        frame before switching to STATE_ACTIVE.

        STATE_COMPLETE: The wave is over, and is either won or lost.

        Parameter dt: The time in seconds since last update
        Precondition: dt is a number (int or float)
        """
        self._welcomeKeyPress()
        self._createWave()
        self._scoreTextObject()
        self._playGame(dt)
        self._deadShip()
        self._pause()
        self._continue()
        self._gameOver()

    def draw(self):
        """
        Draws the game objects to the view.
        """
        if self._state == STATE_INACTIVE:
            self._text.draw(self.view)
        if self._state == STATE_NEWWAVE:
            self._wave.draw(self.view)
            self._scoretext.draw(self.view)
        if self._state == STATE_ACTIVE:
            self._wave.draw(self.view)
            self._scoretext.draw(self.view)
        if self._state == STATE_PAUSED:
            self._text.draw(self.view)
        if self._state == STATE_CONTINUE:
            self._wave.draw(self.view)
        if self._state == STATE_COMPLETE:
            self._wave.clearBolts()
            self._scoretext.draw(self.view)
            self._keepScore()
            self._text.draw(self.view)
            self._wave.draw(self.view)
        if self._state == STATE_INACTIVE:
            self._text.draw(self.view)

    def _playGame(self, dt):
        """
        Helper method that checks if all aliens or destroyed or if the aliens
        have succesfully entered past the Defense Line.

        It also updates the wave if state is STATE_ACTIVE
        """
        if self._state == STATE_ACTIVE:
            if self._wave.checkAliens() or self._wave._alienInvasion():
                self._state = STATE_COMPLETE
            self._wave.update(dt, self.input)
            self._keepScore()

    def _continue(self):
        """
        Helper method that checks for STATE_CONTINUE, and then creates a new
        ship if True. It also sets the state as STATE_ACTIVE.
        """
        if self._state == STATE_CONTINUE:
            self._wave.setNewShip()
            self._state = STATE_ACTIVE

    def _createWave(self):
        """
        Helper method that checks for STATE_NEWWAVE, and then creates a wave.
        It also sets the state as STATE_ACTIVE
        """
        if self._state == STATE_NEWWAVE:
            self._wave = Wave()
            self._wave.increaseSpeed(self._numwaves)
            self._state = STATE_ACTIVE

    def _deadShip(self):
        """
        Helper method that checks for the ship being destroyed. If it is
        destroyed and there are enough lives, it pauses the game. Otherwise, it
        completes the game.
        """
        if self._state == STATE_ACTIVE and self._wave.shipDead():
            if self._wave.getLives() > 0:
                self._state = STATE_PAUSED
                self._lastkeypress = False
            else:
                self._state = STATE_COMPLETE

    def _scoreTextObject(self):
        """
        Helper method that creates a GLabel object and stores it in
        self._scoretext
        """
        self._scoretext = GLabel(
            text="Score: ",
            font_size = 20,
            x = GAME_WIDTH - ALIEN_WIDTH * 3 ,
            y = GAME_HEIGHT - ALIEN_CEILING / 2,
            font_name = 'RetroGame.ttf')

    def _keepScore(self):
        """
        Accumulates the score from destroying aliens, and adds it to
        total score. Changes text of _scoretext Glabel object to refer to
        current score
        """
        self._score += self._wave.getScore()
        self._scoretext.text = "Score: " + str(self._score)

    def _pause(self):
        """
        Helper method which configures the STATE_PAUSED state, and writes
        a message. It also activates key press method to leave pause state
        """
        if self._state == STATE_PAUSED:
            self._wave.clearBolts()
            if self._wave.getLives() > 1:
                life = str(self._wave.getLives()) + " lives"
            else:
                life = str(self._wave.getLives()) + " life"
            self._text = GLabel(
                text=("You have lost a life!"
                    "\nYou have " + life +
                    " remaining"
                    "\n\nPress 'k' to continue."
                    ),
                font_size = 29,
                x = GAME_WIDTH / 2,
                y = GAME_HEIGHT / 2,
                font_name = 'RetroGame.ttf')
            self._pauseKeyPress()

    def _gameOver(self):
        """
        Helper method which writes the appropriate message if you win or lose
        the game.
        """
        if self._state == STATE_COMPLETE:
            if self._wave.getLives() > 0:
                self._text = GLabel(
                    text="You have defeated the aliens..."
                        " But there's more!"
                        "\nPress 'k' to fight the next wave",
                    font_size = 29,
                    x = GAME_WIDTH / 2,
                    y = GAME_HEIGHT / 2,
                    font_name = 'RetroGame.ttf')
                self._lastkeypress = False
                self._nextWaveKeyPress()

            else:
                self._text = GLabel(
                    text="The aliens have succeeded... Game over."
                    "\nPress 'k' to Restart!",
                    font_size = 24,
                    x = GAME_WIDTH / 2,
                    y = SHIP_BOTTOM * 1.5,
                    font_name = 'RetroGame.ttf')
                self._lastkeypress = False
                self._gameOverKeyPress()


    def _nextWaveKeyPress(self):
        """
        Checks for the right key press, and then assigns state to self._state

        This method checks for the key 'k' being pressed. If 'k' is pressed, it
        changes the state to STATE_NEWWAVE.
        Returns True if the correct key is pressed.
        """
        currentkeys = self.input.key_count
        correctState = self._state == STATE_COMPLETE
        pressedkeys = currentkeys > 0 and self._lastkeypress == False
        correctkey = (pressedkeys and self.input.is_key_down('k') and
            correctState)

        if correctkey:
            self._state = STATE_NEWWAVE
            self._lastkeypress = True
            self._text = None
            self._numwaves += 1
        return True

    def _welcomeKeyPress(self):
        """
        Checks for the right key press, and then assigns state to self._state

        This method checks for the key 'k' being pressed. If 'k' is pressed, it
        changes the state to STATE_NEWWAVE.
        Returns True if the correct key is pressed.
        """
        currentkeys = self.input.key_count
        correctState = self._state == STATE_INACTIVE
        pressedkeys = currentkeys > 0 and self._lastkeypress == False
        correctkey = (pressedkeys and self.input.is_key_down('k') and
            correctState)

        if correctkey:
            self._state = STATE_NEWWAVE
            self._lastkeypress = True
            self._text = None
        return True

    def _gameOverKeyPress(self):
        """
        Checks for the right key press, and then assigns state to self._state

        This method checks for the key 'k' being pressed. If 'k' is pressed, it
        restarts the game.
        Returns True if the correct key is pressed.
        """
        currentkeys = self.input.key_count
        correctState = self._state == STATE_COMPLETE
        pressedkeys = currentkeys > 0 and self._lastkeypress == False
        correctkey = (pressedkeys and self.input.is_key_down('k') and
            correctState)
        if correctkey:
            self.start()
        return True

    def _pauseKeyPress(self):
        """
        Checks for the right key press, and then assigns state to self._state

        This method checks for the key 'k' being pressed. If 'k' is pressed, it
        changes the state to STATE_ACTIVE.
        Returns True if the correct key is pressed.
        """
        currentkeys = self.input.key_count
        correctState = self._state == STATE_PAUSED
        pressedkeys = currentkeys > 0 and self._lastkeypress == False
        correctkey = (pressedkeys and self.input.is_key_down('k') and
            correctState)

        if correctkey:
            self._state = STATE_CONTINUE
            self._lastkeypress = True
            self._text = None
