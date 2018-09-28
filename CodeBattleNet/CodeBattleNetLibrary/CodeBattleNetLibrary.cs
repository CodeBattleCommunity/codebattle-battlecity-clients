using System;
using System.Collections.Generic;
using WebSocket4Net;

namespace CodeBattleNetLibrary
{
    public class GameClientBattlecity
    {
        private readonly WebSocket _socket;
        private event Action OnUpdate;

        public Elements[,] Map { get; private set; }
        public int MapSize { get; private set; }
        public int PlayerX { get; private set; }
        public int PlayerY { get; private set; }

        public GameClientBattlecity(string server, string userEmail, string userPassword = null)
        {
            MapSize = 0;

            _socket =
                new WebSocket(
                    $"ws://{server}/codenjoy-contest/ws?user={userEmail}{(string.IsNullOrEmpty(userPassword) ? string.Empty : $"&code={userPassword}")}");
            _socket.MessageReceived += (s, e) => { ParseField(e.Message); };
        }

        private void ParseField(string rawField)
        {
            rawField = rawField.Substring(6);
            int size = (int)Math.Sqrt(rawField.Length);
            if (MapSize != size)
            {
                Map = new Elements[size, size];
                MapSize = size;
            }

            int rawPosition = 0;
            for (int j = 0; j < size; j++)
            {
                for (int i = 0; i < size; i++)
                {
                    Map[i, j] = CharToBlock(rawField[rawPosition]);

                    if (IsPlayerCoords(Map[i, j]))
                    {
                        PlayerX = i;
                        PlayerY = j;
                    }

                    rawPosition++;
                }
            }

            OnUpdate?.Invoke();
        }

        protected bool IsPlayerCoords(Elements block) => block == Elements.TANK_DOWN ||
                                                                block == Elements.TANK_LEFT ||
                                                                block == Elements.TANK_RIGHT ||
                                                                block == Elements.TANK_UP;

        protected Elements CharToBlock(char c) =>
            Enum.IsDefined(typeof(Elements), (int)c)
                ? (Elements)c
                : Elements.Unknown;

        public void Run(Action handler)
        {
            OnUpdate += handler;
            _socket.Open();
        }

        public void SendActions(string commands)
        {
            _socket.Send(commands);
        }

        public string Up() => "UP";

        public string Down() => "DOWN";

        public string Right() => "RIGHT";

        public string Left() => "LEFT";

        public string Act() => "ACT";

        public string Blank() => "";

        public Point GetPlayerTank() => new Point(PlayerX, PlayerY);

        public List<Point> GetOtherPlayersTanks() => 
            FindingCoordinatesOfElements(
                Elements.OTHER_TANK_DOWN, 
                Elements.OTHER_TANK_LEFT, 
                Elements.OTHER_TANK_RIGHT, 
                Elements.OTHER_TANK_UP);

        public List<Point> GetBotsTanks() => 
            FindingCoordinatesOfElements(
                Elements.AI_TANK_DOWN, 
                Elements.AI_TANK_LEFT, 
                Elements.AI_TANK_RIGHT, 
                Elements.AI_TANK_UP);

        public List<Point> GetBullets() => 
            FindingCoordinatesOfElements(
                Elements.BULLET_DOWN,
                Elements.BULLET_LEFT,
                Elements.BULLET_RIGHT,
                Elements.BULLET_UP);

        public List<Point> GetWormholes() => FindingCoordinatesOfElements(Elements.WORM_HOLE);

        public List<Point> GetConstructions() => FindingCoordinatesOfElements(Elements.CONSTRUCTION);

        public List<Point> GetDestroyedConstructions() =>
            FindingCoordinatesOfElements(
                Elements.CONSTRUCTION_DESTROYED_DOWN,
                Elements.CONSTRUCTION_DESTROYED_DOWN_LEFT,
                Elements.CONSTRUCTION_DESTROYED_DOWN_RIGHT,
                Elements.CONSTRUCTION_DESTROYED_DOWN_TWICE,
                Elements.CONSTRUCTION_DESTROYED_LEFT,
                Elements.CONSTRUCTION_DESTROYED_LEFT_RIGHT,
                Elements.CONSTRUCTION_DESTROYED_LEFT_TWICE,
                Elements.CONSTRUCTION_DESTROYED_RIGHT,
                Elements.CONSTRUCTION_DESTROYED_RIGHT_TWICE,
                Elements.CONSTRUCTION_DESTROYED_RIGHT_UP,
                Elements.CONSTRUCTION_DESTROYED_UP,
                Elements.CONSTRUCTION_DESTROYED_UP_DOWN,
                Elements.CONSTRUCTION_DESTROYED_DOWN,
                Elements.CONSTRUCTION_DESTROYED_UP_LEFT,
                Elements.CONSTRUCTION_DESTROYED_UP_TWICE);

        public List<Point> GetBogs() => FindingCoordinatesOfElements(Elements.BOG);

        public List<Point> GetSands() => FindingCoordinatesOfElements(Elements.SAND);

        public List<Point> GetMoats() =>
            FindingCoordinatesOfElements(
                Elements.MOAT_HORIZONTAL,
                Elements.MOAT_VERTICAL);

        public List<Point> GetHedgehogs() => FindingCoordinatesOfElements(Elements.HEDGEHOG);

        public List<Point> GetWalls() => FindingCoordinatesOfElements(Elements.BATTLE_WALL);

        public List<Point> GetBarriers()
        {
            List<Point> barriers = new List<Point>();
            barriers.AddRange(GetWalls());
            barriers.AddRange(GetConstructions());
            barriers.AddRange(GetDestroyedConstructions());
            barriers.AddRange(GetOtherPlayersTanks());
            barriers.AddRange(GetBotsTanks());
            barriers.AddRange(GetHedgehogs());
            return barriers;
        }

        public List<Point> GetAmmoBonuses() => FindingCoordinatesOfElements(Elements.BONUS_AMMO);

        public List<Point> GetMedKitBonuses() => FindingCoordinatesOfElements(Elements.MEDKIT);

        public bool IsOutOf(int x, int y) => x >= MapSize || y >= MapSize || x < 0 || y < 0;

        public bool IsAt(int x, int y, Elements element) => IsOutOf(x, y) ? false : (Map[x, y] == element);

        public bool IsAnyOfAt(int x, int y, params Elements[] elements) => Array.Exists(elements, element => IsAt(x, y, element));

        public bool IsBarrierAt(int x, int y) => GetBarriers().Exists(barrier => IsAt(x, y, Map[barrier.X, barrier.Y]));

        public bool IsNear(int x, int y, Elements element) => 
            IsAt(x - 1, y, element) ||
            IsAt(x + 1, y, element) ||
            IsAt(x, y - 1, element) ||
            IsAt(x, y + 1, element);

        public int CountNear(int x, int y, Elements element)
        {
            int count = 0;
            if (IsAt(x - 1, y, element)) count++;
            if (IsAt(x + 1, y, element)) count++;
            if (IsAt(x, y - 1, element)) count++;
            if (IsAt(x, y + 1, element)) count++;
            return count;
        }

        private List<Point> FindingCoordinatesOfElements(params Elements[] elements)
        {
            List<Point> points = new List<Point>();

            for (int j = 0; j < MapSize; j++)
            {
                for (int i = 0; i < MapSize; i++)
                {
                    if (Array.Exists(elements, element => IsAt(i, j, element)))
                    {
                        points.Add(new Point(i, j));
                    }
                }
            }
            return points;
        }
    }
}
