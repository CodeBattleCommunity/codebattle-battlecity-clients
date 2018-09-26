#!/usr/bin/env python3

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

from sys import version_info
from webclient import WebClient
from dds import DirectionSolver

assert version_info[0] == 3, "You should run me with Python 3.x"


def main():

    dds = DirectionSolver()
    wcl = WebClient(dds)

    server = 'ws://localhost:8080/codenjoy-contest/ws'
    user = 'test@mail.ru'
    code = '1351323692637632922'
    wcl.run(server, user, code)

if __name__ == '__main__':
    main()
