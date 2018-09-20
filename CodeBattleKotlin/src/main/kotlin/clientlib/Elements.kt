package clientlib

enum class Elements(val element: Char, val power: Int = 0) {

    NONE(' '),
    BATTLE_WALL('☼'),
    BANG('Ѡ'),
    BONUS_AMMO('◊'),
    MEDKIT('☺'),

    CONSTRUCTION('╬', 3),
    CONSTRUCTION_DESTROYED_DOWN('╩', 2),
    CONSTRUCTION_DESTROYED_UP('╦', 2),
    CONSTRUCTION_DESTROYED_LEFT('╠', 2),
    CONSTRUCTION_DESTROYED_RIGHT('╣', 2),
    CONSTRUCTION_DESTROYED_DOWN_TWICE('╨', 1),
    CONSTRUCTION_DESTROYED_UP_TWICE('╥', 1),
    CONSTRUCTION_DESTROYED_LEFT_TWICE('╞', 1),
    CONSTRUCTION_DESTROYED_RIGHT_TWICE('╡', 1),
    CONSTRUCTION_DESTROYED_LEFT_RIGHT('│', 1),
    CONSTRUCTION_DESTROYED_UP_DOWN('─', 1),
    CONSTRUCTION_DESTROYED_UP_LEFT('┌', 1),
    CONSTRUCTION_DESTROYED_RIGHT_UP('┐', 1),
    CONSTRUCTION_DESTROYED_DOWN_LEFT('└', 1),
    CONSTRUCTION_DESTROYED_DOWN_RIGHT('┘', 1),
    CONSTRUCTION_DESTROYED(' ', 0),

    BULLET('•'),

    TANK_UP('▲'),
    TANK_RIGHT('►'),
    TANK_DOWN('▼'),
    TANK_LEFT('◄'),

    OTHER_TANK_UP('˄'),
    OTHER_TANK_RIGHT('˃'),
    OTHER_TANK_DOWN('˅'),
    OTHER_TANK_LEFT('˂'),

    AI_TANK_UP('?'),
    AI_TANK_RIGHT('»'),
    AI_TANK_DOWN('¿'),
    AI_TANK_LEFT('«'),

    WORM_HOLE('ʘ'),
    SAND('□'),
    MOAT_HORIZONTAL('='),
    MOAT_VERTICAL('‖'),
    HEDGEHOG('ͱ'),
    BOG('@');

    BULLET_UP('↥'),
    BULLET_RIGHT('↦'),
    BULLET_DOWN('↧'),
    BULLET_LEFT('↤')

    override fun toString(): String = element.toString()
}