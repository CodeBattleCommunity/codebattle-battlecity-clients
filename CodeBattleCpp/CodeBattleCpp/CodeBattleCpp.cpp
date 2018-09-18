#include <iostream>
#include <random>

#include "GameClientBattlecity.h"
#include "BattlecityBlocks.h"
#include "Point.h"


void main()
{
	srand(time(0));
	GameClientBattlecity *gcb = new GameClientBattlecity("localhost:8080", "test@mail.ru", "1351323692637632922");
	
	gcb->Run([&]()
	{
		bool done = false;

		gcb->Right(FireAction::AfterTurn);
		done = true;

		if (done == false)
			gcb->Blank();

	});

	getchar();
}
