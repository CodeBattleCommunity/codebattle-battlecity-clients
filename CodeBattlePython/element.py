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


_ELEMENTS = dict(
    CONSTRUCTION = '╬',

    CONSTRUCTION_DESTROYED_DOWN = '╩',
    CONSTRUCTION_DESTROYED_UP = '╦',
    CONSTRUCTION_DESTROYED_LEFT = '╠',
    CONSTRUCTION_DESTROYED_RIGHT = '╣',
    CONSTRUCTION_DESTROYED_DOWN_TWICE = '╨',
    CONSTRUCTION_DESTROYED_UP_TWICE = '╥',
    CONSTRUCTION_DESTROYED_LEFT_TWICE = '╞',
    CONSTRUCTION_DESTROYED_RIGHT_TWICE = '╡',
    CONSTRUCTION_DESTROYED_LEFT_RIGHT = '│',
    CONSTRUCTION_DESTROYED_UP_DOWN = '─',
    CONSTRUCTION_DESTROYED_UP_LEFT = '┌',
    CONSTRUCTION_DESTROYED_UP_RIGHT = '┐',
    CONSTRUCTION_DESTROYED_DOWN_LEFT = '└',
    CONSTRUCTION_DESTROYED_DOWN_RIGHT = '┘',

    BULLET = '•',

    AI_TANK_UP = '?',
    AI_TANK_RIGHT = '»',
    AI_TANK_DOWN = '¿',
    AI_TANK_LEFT = '«',

    OTHER_TANK_UP = '˄',
    OTHER_TANK_RIGHT = '˃',
    OTHER_TANK_DOWN = '˅',
    OTHER_TANK_LEFT = '˂',

    TANK_UP = '▲',
    TANK_RIGHT = '►',
    TANK_DOWN = '▼',
    TANK_LEFT = '◄',

    WORM_HOLE = 'ʘ',
    BOG = '@',
    SAND = '□',
    MOAT_HORIZONTAL = '=',
    MOAT_VERTICAL = '‖',
    HEDGEHOG = 'ͱ',
    BATTLE_WALL = '☼',
    BANG = 'Ѡ',
    BONUS_AMMO = '◊',
    MEDICINE = '☺',

    NONE = ' '
)


def value_of(char):
    """ Test whether the char is valid Element and return it's name."""
    for value, c in _ELEMENTS.items():
        if char == c:
            return value
    else:
        raise ArgumentError("No such Element: {}".format(char))


class Element:
    """ Class describes the Element objects for Battlecity game."""
    def __init__(self, n_or_c):
        """ Construct an Element object from given name or char."""
        for n, c in _ELEMENTS.items():
            if n_or_c == n or n_or_c == c:
                self._name = n
                self._char = c
                break
        else:
            raise ArgumentError("No such Element: {}".format(n_or_c))

    def get_char(self):
        """ Return the Element's character."""
        return self._char

    def __eq__(self, otherElement):
        return (self._name == otherElement._name and
                self._char == otherElement._char)


class ArgumentError(Exception):
    def __init__(self, message):
            super().__init__(message)
