using System;
using System.Collections.Generic;
using CodeBattleNetLibrary;

namespace CodeBattleNet
{
    internal static class Program
    {
        private static void Main()
        {
            var game = new GameClientBattlecity("127.0.0.1:8080", "a@mail.com", "16063299111614783155");
            game.Run(() =>
            {
                game.SendActions(Direction.RIGHT + "," + Direction.ACT);
				
            });
            Console.Read();
        }
    }
}
