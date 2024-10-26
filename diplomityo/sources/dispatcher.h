#ifndef CDISPATCHER_H
#define CDISPATCHER_H

#include "action.h"
#include "actiondefinitions.h"
#include "seminole.h"
#include "parser.h"
#include "home.h"
#include "memory_home.h"
#include "object_type.h"
#include "request.h"

#define MAX_NUMBER_OF_ACTIONS 200

class CDispatcher {
 public:
  CDispatcher();
  ~CDispatcher();
  bool Init();
  CParser *GetParser() const;
  AttrFlags GetPrevErrFlags();
  CAction *GetAction(enum WebUser user, const string action, const string objnm, RequestMap &req, error_t error);
  CAction *GetAction(CRequest *req);
  home *GetHomePtr(const object_type *pType);
 private:
  CAction *nullacn;
  CAction *prevAction;
  object_type_list *pList;
  CActionDefinitions *m_pDefinitions;
  CParser *m_pParser;
  AttrFlags null_flags;
  ActionMap createdActionsMap;
  CAction *GetActionByKey(enum WebUser user, const string action, const string objnm, const bool feedback);
  CAction *GetNullAction() const;

 

  // memhomes
  int InitHomes();
  memory_home *p_brhome;
  memory_home *p_ifhome;
  memory_home *p_dnshome;
  memory_home *p_rthome;
  memory_home *p_snmpchome;
  memory_home *p_vlanhome;

};

#endif

