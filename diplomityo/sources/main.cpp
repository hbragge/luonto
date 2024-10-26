#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "seminole.h"
#include "demo.h"
#include "globals.h"
#include "sitedata.h"

int  Initialize_The_Web_Server (void);
void failure                   (const char *const,const int,const char *const);

int main(int argc,char *argv[])
{
  Initialize_The_Web_Server();
  gp_WebServer->Start();

  char buf[4];
  printf("Press <ENTER> to stop.\n");
  fgets(buf,sizeof(buf),stdin);
  
#if HTTPD_INC_SHUTDOWN
  gp_WebServer->Stop(HARD);
#endif

  return (EXIT_SUCCESS);
}

int Initialize_The_Web_Server(void)
{
  // initialize the operating system abstraction layer
  if (HttpdOpSys::Init() != 0)
    failure(__FILE__,__LINE__,"HttpdOpSys::Init()");
  
  // create the webserver instance.  This just initializes
  // the object---it doesn't start it up
  gp_WebServer = new Httpd(c_servername,c_serverport);
  if (gp_WebServer == NULL)
    failure(__FILE__,__LINE__,"Httpd::Httpd()");

  static HttpdMemoryDataSource builtin(SemWeb,sizeof(SemWeb));
  HttpdRomFileSystem *promfs = new HttpdRomFileSystem;
  if (promfs->Mount(&builtin) != 0)
    failure(__FILE__,__LINE__,"HttpdRomFileSystem::Mount()");

  
  // install DemoHandler for root
  HttpdHandler *phand = new DemoHandler(promfs,"/","/");

  if (phand == NULL)
    failure(__FILE__,__LINE__,"DemoHandler::DemoHandler()");

  gp_WebServer->Install(phand);

  printf("Server started on host %s port %d\n", c_servername, c_serverport);

  return (1);
}

void failure(const char *const file,const int line,const char *const msg)
{
  assert(file != NULL);
  assert(line >  0);
  assert(msg  != NULL);
  
  fprintf(stderr,"%s:%d Call to %s failed\n",file,line,msg);
  exit(EXIT_FAILURE);
}
