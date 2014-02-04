#include "fsm.h"
#include <stdio.h>

int state = STATE_Off;
int onoff_pressed = 0;

void sm_switch(int new_state)
{
	if (state)
		printf("STATE_On, ");
	else
		printf("STATE_Off, ");

	if (new_state)
		printf("setting STATE_On\n");
	else
		printf("setting STATE_Off\n");

	state = new_state;
}

int main(void) {
	sm_trigger();
	onoff_pressed = 1;
	sm_trigger();
	sm_trigger();
	sm_trigger();

	return 0;
}
