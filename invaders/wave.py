"""
Subcontroller module for Alien Invaders

This module contains the subcontroller to manage a single level or wave in the Alien
Invaders game.  Instances of Wave represent a single wave.

Rishab Kaul rk493
12/3/2018
"""
from game2d import *
from consts import *
from models import *
import random

class Wave(object):
    """
    This class controls a single level or wave of Alien Invaders.

    This subcontroller has a reference to the ship, aliens, and any laser bolts on screen.
    It animates the laser bolts, removing any aliens as necessary. It also marches the
    aliens back and forth across the screen until they are all destroyed or they reach
    the defense line (at which point the player loses).

    INSTANCE ATTRIBUTES:
        _ship:   the player ship to control [Ship]
        _aliens: the 2d list of aliens in the wave [rectangular 2d list of Alien or None]
        _bolts:  the laser bolts currently on screen [list of Bolt, possibly empty]
        _dline:  the defensive line being protected [GPath]
        _lives:  the number of lives left  [int >= 0]
        _time:   The amount of time since the last Alien "step" [number >= 0]

    ADDITIONAL ATTRIBUTES:
        _direction: The direction that the aliens are moving in [String 'left' or 'right']
        _fired: Whether a player bolt already exists or not. [True or False]
        _alienrate: Steps until the aliens fire next [int >= 1]
        _steps: Amount of steps aliens have taken [int >= 0]
        _speedmodifier: Modifies the speed of wave after another wave creation [0 < int < 1]
        _alienspeed: speed of alien [0 < float <= 1]
        _score: game score int >= 0
        _scoreincrease: True if score increased, false otherwise [True or False]
    """

    # GETTERS AND SETTERS
    def getScore(self):
        """
        Gets the score of the game
        Returns the game score (int >= 0)
        """
        if self._scoreincrease == True:
            self._scoreincrease = False
            return self._score
        else:
            return 0

    def getLives(self):
        """
        Returns lives left
        """
        return self._lives

    def clearBolts(self):
        """
        Sets the list _bolts as empty
        """
        self._bolts = []

    def setNewShip(self):
        """
        Creates a new ship
        """
        self._ship = Ship(x = GAME_WIDTH / 2,
            y = SHIP_HEIGHT / 2 + SHIP_BOTTOM,
            width = SHIP_WIDTH,
            height = SHIP_HEIGHT,
            source = 'ship.png')

    def increaseSpeed(self, x):
        """
        Decreases the value of self_alienspeed so that the speed of aliens
        becomes faster

        Parameter x the number of waves that have been completed.
        int x >= 0
        """
        if self._alienspeed < 0.5:
            self._alienspeed = 0.05
        elif x < 6:
            self._alienspeed -= (0.15 * x)
        elif x > 5 and x < 8:
            self._alienspeed -= ((0.15 * 5) + 0.08 * (x - 5))
        else:
            self._alienspeed -= 0.95

    # INITIALIZER TO CREATE SHIP AND ALIENS
    def __init__(self):
        """
        Initializes a new wave of Aliens Invaders.

        Utilizes helper methods to create a 2d list of aliens in the wave
        (_aliens)
        Initializes self._fired as False
        Assigns a ship object to self._ship
        Creates a defense line and assigns it to self._dline
        Initializes self._time and self._steps and assigns 0
        Assigns an empty list to self._bolts
        Initializes self._alienrate as a random number 1 <= x < BOLT_RATE +
        Initializes starting self._alienspeed as ALIEN_SPEED
        Initializes self._score as 0
        Initializes self._scoreincrease as False
        """
        self._fired = False
        self._aliens = self._createAliens()
        self._ship = Ship(x = GAME_WIDTH / 2,
            y = SHIP_HEIGHT / 2 + SHIP_BOTTOM,
            width = SHIP_WIDTH,
            height = SHIP_HEIGHT,
            source = 'ship.png')
        self._dline = self._createDefenseLine()
        self._direction = 'right'
        self._time = 0
        self._bolts = []
        self._alienrate = random.randrange(1, BOLT_RATE + 1)
        self._steps = 0
        self._lives = 3
        self._alienspeed = ALIEN_SPEED
        self._score = 0
        self._scoreincrease = False

    # UPDATE METHOD TO MOVE THE SHIP, ALIENS, AND LASER BOLTS
    def update(self, dt, input):
        """
        Animates the ship, aliens, and laser bolts

        Parameter input: the user input, used to control the ship and change state
        Precondition: instance of GInput

        Parameter dt: time since last animation frame
        Precondition: dt is a float
        """
        self._shipMove(input)
        self._alienMove(dt)
        self._shipFire(input)
        if self._steps > self._alienrate:
            self._alienFire()
            self._steps = 0
        self._moveBolt()

    # DRAW METHOD TO DRAW THE SHIP, ALIENS, DEFENSIVE LINE AND BOLTS
    def draw(self, view):
        """
        Draws the ship, aliens, defensive lines, and bolts.

        Parameter view: the game view, used to draw
        Precondition: instance of GView
        """
        for row in self._aliens:
            for alien in row:
                if alien != None:
                    alien.draw(view)
        if self._ship != None:
            self._ship.draw(view)
        self._dline.draw(view)
        for bolt in self._bolts:
            bolt.draw(view)

    # HELPER METHODS
    def shipDead(self):
        """
        Checks to see if the ship has been destroyed. If so, method returns
        True. False Otherwise.
        """
        if self._ship == None:
            return True
        else:
            return False

    def checkAliens(self):
        """
        Helper method. Checks to see if all aliens are destroyed or not.
        Returns boolean destroyed, which is False when aliens are not
        destroyed, and True if they are destroyed.
        """
        destroyed = True
        for row in range(len(self._aliens)):
            for index in range(len(self._aliens[row])):
                if self._aliens[row][index] != None:
                    destroyed = False
        return destroyed

    def _moveBolt(self):
        """
        Moves all bolts
        Deletes bolts that go out the window
        """
        for bolt in self._bolts:
            bolt.move()
            for row in range(len(self._aliens)):
                for index in range(len(self._aliens[row])):
                    if self._aliens[row][index] != None:
                        if self._aliens[row][index].collides(bolt):
                            self._alienScore(self._aliens[row][index])
                            self._scoreincrease = True
                            self._aliens[row][index] = None
            if self._ship != None:
                 if self._ship.collides(bolt):
                     self._ship = None
                     self._lives -= 1
        s = 0
        while s < len(self._bolts):
            if (self._bolts[s].getCollision()):
                del self._bolts[s]
                self._fired = False
            else:
                s += 1
        self._outOfBounds()

    def _alienScore(self, alien):
        """
        Determines the score of alien being destroyed

        Parameter alien an Alien object
        """
        self._score = 0
        if alien.y > GAME_HEIGHT - ALIEN_CEILING * 2:
            self._score += 40
        elif alien.y > GAME_HEIGHT / 2:
            self._score += 20
        elif alien.y <= GAME_HEIGHT / 2:
            self._score += 10

    def _outOfBounds(self):
        """
        Removes bolts that go out of the window
        """
        s = 0
        while s < len(self._bolts):
            if self._bolts[s].y > GAME_HEIGHT:
                del self._bolts[s]
                self._fired = False
            elif self._bolts[s].y < 0:
                del self._bolts[s]
            else:
                s = s + 1

    def _emptyColumn(self, index):
        """
        Checks to see if a specific column's elements are all None or not

        Returns True if it is empty, and False if not
        """
        list = [x[index] for x in self._aliens]
        count = 0
        for spot in list:
            if spot == None:
                count -= 1
            else:
                count += 1
        if count >= 0:
            return False
        else:
            return True

    def _pickColumn(self):
        """
        Helper method, picks a column that does not have all None aliens

        Returns a column of aliens which does not have None as all elements
        """
        randomIndex = random.randrange(0,len(self._aliens[0]))
        i = False
        while i == False:
            for x in range(len(self._aliens)):
                if [s[
                randomIndex] for s in self._aliens].count(None) == ALIEN_ROWS:
                    randomIndex = random.randrange(x,len(self._aliens[0]))
                else:
                    i = True
        return [x[randomIndex] for x in self._aliens]

    def _pickAlien(self):
        """
        Helper method that picks a random alien from list of aliens

        Returns a random alien object at the bottom of a random non-None
        column
        """
        column = self._pickColumn()
        balien = column[0]
        k = 0
        while k < len(column):
            if column[k] == None:
                k += 1
            else:
                min = GAME_WIDTH
                for x in range(len(column)):
                    if column[k] != None and column[k].y < min:
                        min = column[k].y
                        balian = column[k]
                return balian
                k += 1
        return balien

    def _alienFire(self):
        """
        Helper method that creates a bolt object fired from the aliens
        """
        origin = self._pickAlien()
        if origin != None:
            newBolt = Bolt(
                BOLT_SPEED * -1,
                x= origin.x,
                y = origin.y - ALIEN_HEIGHT / 2)
            self._bolts.append(newBolt)
            self._alienrate = random.randrange(1, BOLT_RATE + 1)

    def _shipFire(self,input):
        """
        Helper method that creates a bolt object on correct keypress

        Creates a bolt object at position x of Ship object, and position y
        above the Ship object
        """
        if (input.is_key_down('spacebar') and
            self._fired == False and self._ship != None):
            newBolt = Bolt(
                BOLT_SPEED,
                x= self._ship.x,
                y = SHIP_BOTTOM + SHIP_HEIGHT / 2)
            self._bolts.append(newBolt)
            self._fired = True

    def _alienMove(self, dt):
        """
        Helper method that enables alien wave to move

        Changes horizontal position x of Alien object
        """
        self._time += dt
        if self._time > self._alienspeed:
            if not self._alienWalkVertical():
                self._alienWalkHorizontal()
            self._time = 0
            self._steps += 1

    def _alienWalkVertical(self):
        """
        Helper method that checks for window boundaries, and moves aliens
        down based on them
        """
        if self._rightOut():
            for row in self._aliens:
                for alien in row:
                    if alien != None:
                        alien.y = alien.y - ALIEN_V_WALK
            self._direction = 'left'
            return True
        elif self._leftOut():
            for row in self._aliens:
                for alien in row:
                    if alien != None:
                        alien.y = alien.y - ALIEN_V_WALK
            self._direction = 'right'
            return True
        return False

    def _alienWalkHorizontal(self):
        """
        Helper method that checks direction of aliens, and moves it to that
        direction
        """
        if self._direction == 'right':
            for row in self._aliens:
                for alien in row:
                    if alien != None:
                        alien.x = alien.x + ALIEN_H_WALK
        elif self._direction == 'left':
            for row in self._aliens:
                for alien in row:
                    if alien != None:
                        alien.x = alien.x - ALIEN_H_WALK

    def _rightOut(self):
        """
        Helper method that checks whether the right most alien is going out of bounds.
        If so, it moves all of the aliens downwards.

        Returns True if the right most alien will go out of bounds next movement,
        False otherwise
        """
        rightAlien = self._aliens[0][0]

        for row in range(len(self._aliens)):
            max = 0
            for index in range(len(self._aliens[row])):
                if self._aliens[row][index] != None:
                    if max == 0:
                        max = self._aliens[row][index].x
                    if self._aliens[row][index].x >= max:
                        min = self._aliens[row][index].x
                        rightAlien = self._aliens[row][index]
        if (rightAlien != None and
            (rightAlien.x + ALIEN_H_WALK + ALIEN_WIDTH / 2) > (
            GAME_WIDTH - ALIEN_H_SEP) and self._direction == 'right'):
            return True
        else:
            return False

    def _leftOut(self):
        """
        Helper method that checks whether the left most alien is going out of bounds.
        If so, it moves all of the aliens downwards.

        Returns True if the left most alien will go out of bounds next movement,
        False otherwise
        """
        leftAlien = self._aliens[0][0]

        for row in range(len(self._aliens)):
            min = GAME_WIDTH
            for index in range(len(self._aliens[row])):
                if self._aliens[row][index] != None:
                    if min == GAME_WIDTH:
                        min = self._aliens[row][index].x
                    if self._aliens[row][index].x <= min:
                        min = self._aliens[row][index].x
                        leftAlien = self._aliens[row][index]
        if (leftAlien != None and
            (leftAlien.x - ALIEN_H_WALK - ALIEN_WIDTH / 2) < (
            0 + ALIEN_H_SEP) and self._direction == 'left'):
            return True
        else:
            return False

    def _shipMove(self,input):
        """
        Helper method that enables ship to move

        Changes the horizontal position x of the Ship object
        """
        move = 0
        if self._ship != None:
            if input.is_key_down('a') and  min(
                self._ship.x - SHIP_MOVEMENT,
                0 + SHIP_WIDTH / 2) == 0 + SHIP_WIDTH / 2:
                move = move - SHIP_MOVEMENT
            if input.is_key_down('d') and max(
                self._ship.x + SHIP_MOVEMENT,
                GAME_WIDTH - SHIP_WIDTH / 2) == GAME_WIDTH - SHIP_WIDTH / 2:
                move = move + SHIP_MOVEMENT
            self._ship.x += move

    def _createAliens(self):
        """
        Helper method to create an 2d list of aliens

        Returns a 2d list of Alien objects
        """
        h = ALIEN_HEIGHT
        w = ALIEN_WIDTH
        vsep = ALIEN_V_SEP
        hsep = ALIEN_H_SEP
        img = ALIEN_IMAGES
        aliens = []
        for num in range(ALIEN_ROWS):
            row = []
            for column in range(ALIENS_IN_ROW):
                if num < len(img) * 2 :
                    s = img[num // 2]
                else:
                    s = img[(num - len(img) * 2) // 2]
                x = self._alienWidthHelper() + column * (h + hsep)
                y = self._alienHeightHelper() - (vsep + h) * (ALIEN_ROWS-num-1)
                alien = Alien(x,y,s)
                row.append(alien)
            aliens.append(row)
        return aliens

    def _alienHeightHelper(self):
        """
        Helper method to help find the y position of an Alien in the window

        Returns y, an int referring to vertical position of first Alien from
        the top of the window.
        """
        y = GAME_HEIGHT - ALIEN_CEILING - ALIEN_HEIGHT / 2
        return y

    def _alienWidthHelper(self):
        """
        Helper method to help find the x position of an Alien in the window

        Returns x, an int referring to the horizontal position fo the first
        Alien from the left of the window.
        """
        x = ALIEN_H_SEP + ALIEN_WIDTH / 2
        return x

    def _createDefenseLine(self):
        """
        Helper method to create the defense line

        Returns a GPath object
        """
        x1 = 0
        x2 = GAME_WIDTH
        y = DEFENSE_LINE
        return GPath(
            points = [x1,y,x2,y],
            linewidth = DEFENSE_L_WIDTH,
            fillcolor = [0,0,0,0.2],
            linecolor = [0,0,0,0.4])

    def _alienInvasion(self):
        """
        Checks for any alien that goes below the defense line
        """
        for row in range(len(self._aliens)):
            for index in range(len(self._aliens[row])):
                if self._aliens[row][index] != None:
                    if self._aliens[row][index].y < DEFENSE_LINE:
                        return True
        return False
