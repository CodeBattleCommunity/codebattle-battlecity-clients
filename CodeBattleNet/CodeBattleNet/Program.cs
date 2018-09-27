using System;
using CodeBattleNetLibrary;

namespace CodeBattleNet
{
    internal static class Program
    {
        private static void Main()
        {
            var gcb = new GameClientBattlecity("127.0.0.1:8080", "a@mail.com", "16063299111614783155");
            gcb.Run(() =>
            {
                Move(gcb);
            });
            Console.Read();
        }

        private static void Move(GameClientBattlecity gcb)
        {
            var r = new Random();
            var done = false;

            switch (r.Next(5))
            {
                case 0:
                    if (!gcb.IsBarrierAt(gcb.PlayerX, gcb.PlayerY - 1))
                    {
                        gcb.SendActions(gcb.Up());
                        done = true;
                    }
                    break;
                case 1:
                    if (!gcb.IsBarrierAt(gcb.PlayerX + 1, gcb.PlayerY))
                    {
                        gcb.SendActions(gcb.Right());
                        done = true;
                    }
                    break;
                case 2:
                    if (!gcb.IsBarrierAt(gcb.PlayerX, gcb.PlayerY + 1))
                    {
                        gcb.SendActions(gcb.Down());
                        done = true;
                    }
                    break;
                case 3:
                    if (!gcb.IsBarrierAt(gcb.PlayerX - 1, gcb.PlayerY))
                    {
                        gcb.SendActions(gcb.Left());
                        done = true;
                    }
                    break;
                case 4:
                    gcb.SendActions(gcb.Act());
                    done = true;
                    break;
            }
            if (done == false)
                gcb.SendActions(gcb.Blank());
        }
    }
}
