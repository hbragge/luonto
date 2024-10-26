#ifndef CCONTROL_H
#define CCONTROL_H

#include "seminole.h"
#include "sem_template.h"
#include "action.h"
#include "dispatcher.h"
#include "request.h"

struct Table
{
  HttpdSymbolTable *symtable;
  HttpdCgiParameter *paramptr;
};

struct Content
{
  const char *filepath;
  Table table;
};

class CControl
{
 public:
  CControl();
  ~CControl();
  Content *ProcessContent(enum WebUser user, const string action, const string objnm, RequestMap *req, CDispatcher *dp1);
  int ProcessRequest(CRequest *req);
 private:
  CDispatcher *dp;
  Content content;
  HttpdSymbolTable *symtbl;
  CDispatcher *m_dp;
  string GetMenuState(const string objnm, RequestMap &req);
};

#endif
