#pragma once

#include <string>
#include <thread>
#include "easywsclient\easywsclient.hpp"
#ifdef _WIN32
#pragma comment( lib, "ws2_32" )
#include <WinSock2.h>
#endif
#include <assert.h>
#include <stdio.h>
#include <iostream>
#include <string>
#include <memory>
#include <list>

#include "BattlecityBlocks.h"
#include "FireAction.h"
#include "Point.h"

class GameClientBattlecity
{
	BattlecityBlocks **map;

	uint32_t map_size;

	easywsclient::WebSocket *web_socket;
	std::string path;

	bool is_running;
	std::thread *work_thread;
	void update_func(std::function<void()> _message_handler);

public:
	GameClientBattlecity(std::string _server, std::string _userEmail, std::string _userPassword = "");
	~GameClientBattlecity();

	void Run(std::function<void()> _message_handler);
	void Up(FireAction _action = FireAction::None)
	{
		send(std::string(_action == FireAction::BeforeTurn ? "ACT," : "") + "UP" + std::string(_action == FireAction::AfterTurn ? ",ACT" : ""));
	}
	void Down(FireAction _action = FireAction::None)
	{
		send(std::string(_action == FireAction::BeforeTurn ? "ACT," : "") + "DOWN" + std::string(_action == FireAction::AfterTurn ? ",ACT" : ""));
	}
	void Right(FireAction _action = FireAction::None)
	{
		send(std::string(_action == FireAction::BeforeTurn ? "ACT," : "") + "RIGHT" + std::string(_action == FireAction::AfterTurn ? ",ACT" : ""));
	}
	void Left(FireAction _action = FireAction::None)
	{
		send(std::string(_action == FireAction::BeforeTurn ? "ACT," : "") + "LEFT" + std::string(_action == FireAction::AfterTurn ? ",ACT" : ""));
	}
	void Act() { 
		send("ACT"); 
	}
	void Blank() { send(""); }

	BattlecityBlocks **get_map() { return map; }
	//std::wstring get_map() const;
	uint32_t get_map_size() { return map_size; }

	Point get_player_tank();
	std::list<Point> get_other_player_tanks();
	std::list<Point> get_bots_tanks();
	std::list<Point> get_bullets();
	std::list<Point> get_wormholes();
	std::list<Point> get_constructions();
	std::list<Point> get_destroyed_constructions();
	std::list<Point> get_bogs();
	std::list<Point> get_sands();
	std::list<Point> get_moats();
	std::list<Point> get_hedgehogs();
	std::list<Point> get_walls();
	std::list<Point> get_barries();
	std::list<Point> get_ammo_bonuses();
	std::list<Point> get_med_kit_bonunes();
	bool is_near(int x, int y, BattlecityBlocks element);
	bool is_barrier_at(int x, int y);
	bool is_at(int x, int y, BattlecityBlocks element);
	std::list<Point> find_all(BattlecityBlocks element);

private:
	void send(std::string msg)
	{
		std::cout << "Sending: " << msg << std::endl;
		web_socket->send(msg);
	}
};
