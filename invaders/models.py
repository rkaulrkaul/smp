"""
Models module for Alien Invaders

This module contains the model classes for the Alien Invaders game. Anything that you
interact with on the screen is model: the ship, the laser bolts, and the aliens.

Rishab Kaul rk493
12/4/2018
"""
from consts import *
from game2d import *


class Ship(GImage):
    """
    A class to represent the game ship.

    _x: Horizonal position of the ship
    [int or float]
    """

    # HELPERS
    def _getx(self):
        """
        Gets x position of the ship
        """
        return self._x

    # INITIALIZER TO CREATE A NEW SHIP
    def __init__(self, x, y, width, height, source):
        """
        Initializes Ship object with the use of GImage

        Parameter x: the horizontal coordinate of object center
        Precondition: x is an int or float

        Parameter y: vertical coordinate of object center
        Precondition: y in an int or float

        Parameter width: width of the object
        Precondition: height is int or float

        Parameter height: height of the object
        Precondition: height is int or float

        Parameter source: source of the object image
        Precondition: source is a valid image file
        """
        super().__init__(x = x, y = y, width = width, height = height, source = source)
        self._x = x

    def collides(self,bolt):
        """
        Returns: True if the bolt was fired by the player and collides with this alien

        Parameter bolt: The laser bolt to check
        Precondition: bolt is of class Bolt
        """
        if ((self.contains((bolt.left, bolt.top)) or
            self.contains((bolt.right, bolt.top)) or
            self.contains((bolt.left, bolt.bottom)) or
            self.contains((bolt.right, bolt.bottom))) and
            (bolt.getVelocity() < 0)):
            bolt.setCollision(True)
            return True
        else:
            return False


class Alien(GImage):
    """
    A class to represent a single alien.

    """

    def __init__(self, x, y, source):
        """
        Initializes an Alien object with the use of GImage

        Parameter x: the horizontal coordinate of object center
        Precondition: x is an int or float

        Parameter y: vertical coordinate of object center
        Precondition: y in an int or float

        Parameter width: width of the object
        Precondition: height is int or float

        Parameter height: height of the object
        Precondition: height is int or float

        Parameter source: source of the object image
        Precondition: source is a valid image file
        """
        super().__init__(x = x, y = y, width = ALIEN_WIDTH, height = ALIEN_HEIGHT, source = source)

    # METHOD TO CHECK FOR COLLISION (IF DESIRED)
    def collides(self,bolt):
        """
        Returns: True if the bolt was fired by the player and collides with this alien

        Parameter bolt: The laser bolt to check
        Precondition: bolt is of class Bolt
        """
        if (self.contains((bolt.left, bolt.top))
            or self.contains((bolt.right, bolt.top))
            or self.contains((bolt.left, bolt.bottom))
            or self.contains((bolt.right, bolt.bottom))):
            if (bolt.getVelocity() > 0):
                self._collision = True
                bolt.setCollision(True)
                return True
        else:
            return False


class Bolt(GRectangle):
    """
    A class representing a laser bolt.

    INSTANCE ATTRIBUTES:
        _velocity: The velocity in y direction [int or float]
        _collision: Whether a Bolt collided or not [Bool, True or False]
    """

    def setCollision(self, bool):
        """
        Sets _collision as true if either alien or ship has collided
        """
        if bool:
            self._collision = True

    def getCollision(self):
        """
        Gets _collision and returns it as True or False
        """
        return self._collision

    def getVelocity(self):
        """
        Gets the velocity of bolt object

        Returns velocity (int)
        """
        return self._velocity

    # INITIALIZER TO SET THE VELOCITY
    def __init__(self, velocity, x, y):
        """
        Initializes a Bolt object
        """
        super().__init__(x = x, y = y, width = BOLT_WIDTH, height = BOLT_HEIGHT, fillcolor = [1,0,0,1])
        self._velocity = velocity
        self._collision = False

    # ADD MORE METHODS (PROPERLY SPECIFIED) AS NECESSARY
    def move(self):
        """
        Moves the bolt up if it has positive velocity, and down otherwise
        """
        if self._isPlayerBolt():
            self.y += self._velocity
        else:
            self.y += self._velocity

    def _isPlayerBolt(self):
        """
        Checks to see if a bolt is from the player
        Returns True if velocity of bold is greater than 0, False
        otherwise
        """
        if self._velocity > 0:
            return True
        else:
            return False
