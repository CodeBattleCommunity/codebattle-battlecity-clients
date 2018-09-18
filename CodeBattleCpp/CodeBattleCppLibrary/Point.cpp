#include "Point.h"

Point::Point(int _x, int _y) {
	x = _x;
	y = _y;
}

int Point::getX() {
	return x;
}

int Point::getY() {
	return y;
}


std::string Point::toString() {
	return std::string("[")+ std::to_string(x) +std::string(",")+ std::to_string(y) +std::string("]");
}

