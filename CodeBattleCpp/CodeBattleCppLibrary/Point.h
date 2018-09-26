#pragma once
#include <iostream>
#include<string>

class Point
{
public:
	Point(int _x = 0, int _y = 0);

	std::string toString();
	int getX();
	int getY();
private:
	int x;
	int y;
};
