#include "GameClientBattlecity.h"

#include <iostream>
#include <sstream>

GameClientBattlecity::GameClientBattlecity(std::string _server, std::string _userEmail, std::string _code)
{
	map_size = 0;

	path = "ws://" + _server + "/codenjoy-contest/ws?user=" + _userEmail + "&code=" + _code;

	is_running = false;
}

GameClientBattlecity::~GameClientBattlecity()
{
	is_running = false;
	work_thread->join();
}

void GameClientBattlecity::Run(std::function<void()> _message_handler)
{
	is_running = true;
	work_thread = new std::thread(&GameClientBattlecity::update_func, this, _message_handler);
}

Point GameClientBattlecity::get_player_tank() {
	std::list<Point> playerTank = find_all(BattlecityBlocks::TANK_DOWN);
	playerTank.splice(playerTank.end(), find_all(BattlecityBlocks::TANK_UP));
	playerTank.splice(playerTank.end(), find_all(BattlecityBlocks::TANK_LEFT));
	playerTank.splice(playerTank.end(), find_all(BattlecityBlocks::TANK_RIGHT));
	return playerTank.front();
}

std::list<Point> GameClientBattlecity::get_other_player_tanks() {
	std::list<Point> otherPlayers = find_all(BattlecityBlocks::OTHER_TANK_DOWN);
	otherPlayers.splice(otherPlayers.end(), find_all(BattlecityBlocks::OTHER_TANK_UP));
	otherPlayers.splice(otherPlayers.end(), find_all(BattlecityBlocks::OTHER_TANK_LEFT));
	otherPlayers.splice(otherPlayers.end(), find_all(BattlecityBlocks::OTHER_TANK_RIGHT));
	return otherPlayers;
}
std::list<Point> GameClientBattlecity::get_bots_tanks() {
	std::list<Point> bots = find_all(BattlecityBlocks::AI_TANK_DOWN);
	bots.splice(bots.end(), find_all(BattlecityBlocks::AI_TANK_UP));
	bots.splice(bots.end(), find_all(BattlecityBlocks::AI_TANK_LEFT));
	bots.splice(bots.end(), find_all(BattlecityBlocks::AI_TANK_RIGHT));
	return bots;
}
std::list<Point> GameClientBattlecity::get_bullets() {
	return find_all(BattlecityBlocks::BULLET);
}
std::list<Point> GameClientBattlecity::get_wormholes() {
	return find_all(BattlecityBlocks::WORM_HOLE);
}
std::list<Point> GameClientBattlecity::get_constructions() {
	return find_all(BattlecityBlocks::CONSTRUCTION);
}
std::list<Point> GameClientBattlecity::get_destroyed_constructions() {
	std::list<Point> destroyedConstructions = find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_DOWN);
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_UP));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_LEFT));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_RIGHT));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_DOWN_TWICE));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_UP_TWICE));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_LEFT_TWICE));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_RIGHT_TWICE));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_LEFT_RIGHT));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_UP_DOWN));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_UP_LEFT));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_UP_RIGHT));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_DOWN_LEFT));
	destroyedConstructions.splice(destroyedConstructions.end(), find_all(BattlecityBlocks::CONSTRUCTION_DESTROYED_DOWN_RIGHT));
	return destroyedConstructions;
}
std::list<Point> GameClientBattlecity::get_bogs() {
	return find_all(BattlecityBlocks::BOG);
}
std::list<Point> GameClientBattlecity::get_sands() {
	return find_all(BattlecityBlocks::SAND);
}
std::list<Point> GameClientBattlecity::get_moats() {
	std::list<Point> moats = find_all(BattlecityBlocks::MOAT_HORIZONTAL);
	moats.splice(moats.end(), find_all(BattlecityBlocks::MOAT_VERTICAL));
	return moats;
}
std::list<Point> GameClientBattlecity::get_hedgehogs() {
	return find_all(BattlecityBlocks::HEDGEHOG);
}
std::list<Point> GameClientBattlecity::get_walls() {
	return find_all(BattlecityBlocks::BATTLE_WALL);
}
std::list<Point> GameClientBattlecity::get_barries() {
	std::list<Point> barriers = get_walls();
	barriers.splice(barriers.end(), get_constructions());
	barriers.splice(barriers.end(), get_destroyed_constructions());
	barriers.splice(barriers.end(), get_other_player_tanks());
	barriers.splice(barriers.end(), get_bots_tanks());
	barriers.splice(barriers.end(), get_hedgehogs());
	return barriers;
}
std::list<Point> GameClientBattlecity::get_ammo_bonuses() {
	return find_all(BattlecityBlocks::BONUS_AMMO);
}
std::list<Point> GameClientBattlecity::get_med_kit_bonunes() {
	return find_all(BattlecityBlocks::MEDKIT);
}
bool GameClientBattlecity::is_near(int x, int y, BattlecityBlocks element) {
	return is_at(x, y - 1, element) || is_at(x, y + 1, element) || is_at(x - 1, y, element) || is_at(x + 1, y, element);
}
bool GameClientBattlecity::is_barrier_at(int x, int y) {
	return true;
}
bool GameClientBattlecity::is_at(int x, int y, BattlecityBlocks element) {
	return map[x][y] == element;
}
std::list<Point> GameClientBattlecity::find_all(BattlecityBlocks element) {

	std::list<Point> out;
	for (uint32_t j = 0; j < map_size; j++)
	{
		for (uint32_t i = 0; i < map_size; i++)
		{
			if (map[j][i] == element) {
				out.push_back(Point(j, i));
			}
		}
	}
	return out;
}

void GameClientBattlecity::update_func(std::function<void()> _message_handler)
{
#ifdef _WIN32
	WSADATA wsaData;

	if (WSAStartup(MAKEWORD(2, 2), &wsaData))
		throw new std::exception("WSAStartup Failed.\n");
	else
		std::cout << "Connection established" << std::endl;
#endif

	web_socket = easywsclient::WebSocket::from_url(path);
	if (web_socket == nullptr)is_running = false;
	while (is_running)
	{
		web_socket->poll();
		web_socket->dispatch([&](const std::string &message)
		{
			int size_needed = MultiByteToWideChar(CP_UTF8, 0, &message[0], (int)message.size(), NULL, 0);
			std::wstring wmessage(size_needed, 0);
			MultiByteToWideChar(CP_UTF8, 0, &message[0], (int)message.size(), &wmessage[0], size_needed);

			uint32_t size = sqrt(wmessage.size() - 6);
			//map = wmessage.substr(6,std::wstring::npos);
			
			
			if (map_size != size)
			{
				if (map_size != 0)
				{
					for (uint32_t j = 0; j < map_size; j++)
						delete[] map[j];
					delete[] map;
				}
				map_size = size;

				map = new BattlecityBlocks*[map_size];
				for (uint32_t j = 0; j < map_size; j++)
				{
					map[j] = new BattlecityBlocks[map_size];
					for (uint32_t i = 0; i < map_size; i++)
					{
						map[j][i] = BattlecityBlocks::Unknown;
					}
				}
			}
			_message_handler();
		});
	}
	if (web_socket)web_socket->close();

#ifdef _WIN32
	WSACleanup();
#endif
}
