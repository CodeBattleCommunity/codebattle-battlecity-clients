#! /usr/bin/env python3

###
# #%L
# Codenjoy - it's a dojo-like platform from developers to developers.
# %%
# Copyright (C) 2016 Codenjoy
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program.  If not, see
# <http://www.gnu.org/licenses/gpl-3.0.html>.
# #L%
###

from math import sqrt
from point import Point
from element import Element


class Board:
    """ Class describes the Board field for Battlecity game."""
    def __init__(self, board_string):
        self._string = board_string.replace('\n', '')
        self._len = len(self._string)  # the length of the string
        self._size = int(sqrt(self._len))  # size of the board
        #print("Board size is sqrt", self._len, self._size)

    def _find_all(self, element):
        """ Returns the list of points for the given element type."""
        _points = []
        _a_char = element.get_char()
        for i, c in enumerate(self._string):
            if c == _a_char:
                _points.append(self._strpos2pt(i))
        return _points

    def at(self, x, y):
        """ Return an Element object at coordinates x,y."""
        return Element(self._string[self._xy2strpos(x, y)])

    def is_at(self, x, y, element_object):
        """ Return True if Element is at x,y coordinates."""
        return element_object == self.at(x, y)

    def is_barrier_at(self, x, y):
        """ Return true if barrier is at x,y."""
        return Point(x, y) in self.barriers

    @property
    def player_tank(self):
        points = []
        points.extend(self._find_all(Element('TANK_UP')))
        points.extend(self._find_all(Element('TANK_DOWN')))
        points.extend(self._find_all(Element('TANK_LEFT')))
        points.extend(self._find_all(Element('TANK_RIGHT')))
        return points[0]

    @property
    def other_players_tanks(self):
        points = []
        points.extend(self._find_all(Element('OTHER_TANK_UP')))
        points.extend(self._find_all(Element('OTHER_TANK_DOWN')))
        points.extend(self._find_all(Element('OTHER_TANK_LEFT')))
        points.extend(self._find_all(Element('OTHER_TANK_RIGHT')))
        return points

    @property
    def bots_tanks(self):
        points = []
        points.extend(self._find_all(Element('AI_TANK_UP')))
        points.extend(self._find_all(Element('AI_TANK_DOWN')))
        points.extend(self._find_all(Element('AI_TANK_LEFT')))
        points.extend(self._find_all(Element('AI_TANK_RIGHT')))
        return points

    @property
    def bullets(self):
        return self._find_all(Element('BULLET'))

    @property
    def worm_holes(self):
        return self._find_all(Element('WORM_HOLE'))

    @property
    def constructions(self):
        return self._find_all(Element('CONSTRUCTION'))

    @property
    def destroyed_constructions(self):
        points = []
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_DOWN')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_UP')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_LEFT')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_RIGHT')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_DOWN_TWICE')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_UP_TWICE')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_LEFT_TWICE')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_RIGHT_TWICE')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_LEFT_RIGHT')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_UP_DOWN')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_UP_LEFT')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_UP_RIGHT')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_DOWN_LEFT')))
        points.extend(self._find_all(Element('CONSTRUCTION_DESTROYED_DOWN_RIGHT')))
        return points

    @property
    def bogs(self):
        return self._find_all(Element('BOG'))

    @property
    def sands(self):
        return self._find_all(Element('SAND'))

    @property
    def moats(self):
        points = []
        points.extend(self._find_all(Element('MOAT_HORIZONTAL')))
        points.extend(self._find_all(Element('MOAT_VERTICAL')))
        return points

    @property
    def hedgehogs(self):
        return self._find_all(Element('HEDGEHOG'))

    @property
    def walls(self):
        return self._find_all(Element('BATTLE_WALL'))

    @property
    def bonus_ammo(self):
        return self._find_all(Element('BONUS_AMMO'))

    @property
    def med_kits(self):
        return self._find_all(Element('MEDICINE'))

    @property
    def barriers(self):
        points = []
        points.extend(self.walls)
        points.extend(self.constructions)
        points.extend(self.destroyed_constructions)
        points.extend(self.other_players_tanks)
        points.extend(self.bots_tanks)
        points.extend(self.hedgehogs)
        return points

    def is_near(self, x, y, elem):
        _is_near = False
        if not Point(x, y).is_bad(self._size):
            _is_near = (self.is_at(x + 1, y, elem) or
                        self.is_at(x - 1, y, elem) or
                        self.is_at(x, 1 + y, elem) or
                        self.is_at(x, 1 - y, elem))
        return _is_near

    def count_near(self, x, y, elem):
        """ Counts the number of occurencies of elem nearby """
        _near_count = 0
        if not Point(x, y).is_bad(self._size):
            for _x, _y in ((x + 1, y), (x - 1, y), (x, 1 + y), (x, 1 - y)):
                if self.is_at(_x, _y, elem):
                    _near_count += 1
        return _near_count

    def __str__(self):
        return ("Board:\n{brd}\nTank at: {tnk}\nOther tanks at: {otk}\n"
                "Bot tanks at: {btk}\nHedgehogs at: {hgh}\nMoats at: {mat}\n"
                "Bogs at: {bog}\nSands at: {snd}".format(
                        brd = self._line_by_line(),
                        tnk = self.player_tank,
                        otk = self.other_players_tanks,
                        btk = self.bots_tanks,
                        hgh = self.hedgehogs,
                        mat = self.moats,
                        bog = self.bogs,
                        snd = self.sands
                )
        )

    def _line_by_line(self):
        return '\n'.join([self._string[i:i + self._size]
                              for i in range(0, self._len, self._size)])

    def _strpos2pt(self, strpos):
        return Point(*self._strpos2xy(strpos))

    def _strpos2xy(self, strpos):
        return (strpos % self._size, strpos // self._size)

    def _xy2strpos(self, x, y):
        return self._size * y + x
