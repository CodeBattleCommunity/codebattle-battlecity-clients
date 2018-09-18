#pragma once

#include <cstdint>

enum class FireAction : uint8_t
{
	None = 0,
	BeforeTurn = 1,
	AfterTurn = 2
};
