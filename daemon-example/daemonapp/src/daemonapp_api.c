#include "daemonapp_api.h"
#include "daemonapp_private.h"
#include <daemonlib_api.h>
#include <stdio.h>

void daemonapp_init(void)
{
  daemonlib_init();
  puts( APP_GREETING "\n");
}
