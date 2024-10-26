#include "dispatcher.h"
#include <map>

CDispatcher::CDispatcher():nullacn(0), prevAction(0), m_pDefinitions(0), m_pParser(0)
{
  if (!Init()) {
    printf("dispatcher init error\n");
    exit(1);
  }
}

CDispatcher::~CDispatcher() 
{
  if (m_pDefinitions) {
    delete m_pDefinitions;
    m_pDefinitions = 0;
  }

  if (nullacn) {
    delete nullacn;
    nullacn = 0;
  }

  if (m_pParser) {
    delete m_pParser;
    m_pParser = 0;
  }
  
  // delete homes
  delete p_brhome;
  delete p_ifhome;
  delete p_dnshome;
  delete p_rthome;
  delete p_snmpchome;
  delete p_vlanhome;
}

int CDispatcher::InitHomes()
{
  // varaa muisti memhomeille, demohandler kutsuu
  const object_type_list *pList = object_type_list::get_instance();

  p_brhome = new memory_home(pList->get_object_type("bridge"));
  p_ifhome = new memory_home(pList->get_object_type("device_interface"));
  p_dnshome = new memory_home(pList->get_object_type("ip_nameserver"));
  p_rthome = new memory_home(pList->get_object_type("ip_static_route"));
  p_snmpchome = new memory_home(pList->get_object_type("snmp_community"));
  p_vlanhome = new memory_home(pList->get_object_type("vlan"));
 
  return (0);
}

home *CDispatcher::GetHomePtr(const object_type *pType)
{
  const string objnm = pType->get_type_name();

  if (objnm.compare("bridge") == 0) {
    return p_brhome;
  } else if (objnm.compare("device_interface") == 0) {
    return p_ifhome;
  } else if (objnm.compare("ip_nameserver") == 0) {
    return p_dnshome;
  } else if (objnm.compare("ip_static_route") == 0) {
    return p_rthome;
  } else if (objnm.compare("snmp_community") == 0) {    
    return p_snmpchome;
  } else if (objnm.compare("vlan") == 0) {
    return p_vlanhome;
  } else {
    return NULL;
  }
}

bool CDispatcher::Init() 
{
  InitHomes();
  nullacn = new CAction("/index.thtm");
  m_pDefinitions = new CActionDefinitions;
  m_pParser = new CParser;
  pList = object_type_list::get_instance();
  
  return (m_pDefinitions->CreateActions(createdActionsMap, pList, this));
}

CParser *CDispatcher::GetParser() const
{
  return m_pParser;
}

CAction *CDispatcher::GetAction(enum WebUser user, const string action, const string objnm, RequestMap &req, error_t error)
{
  return (0);
}

CAction *CDispatcher::GetAction(CRequest *req)
{
  printf("dp: getting action: %i %s %s\n",req->GetUser(), req->GetActionType().c_str(), req->GetObjnm().c_str());

  return GetActionByKey(req->GetUser(), req->GetActionType(), req->GetObjnm(), false);
}

CAction *CDispatcher::GetActionByKey(enum WebUser user, const string action, const string objnm, const bool feedback)
{
  printf("dp: getting action: %i %s %s\n",user,action.c_str(),objnm.c_str());
  CActionKey key_defined(user, action, objnm);
  ActionMap::iterator iter_defined = createdActionsMap.find(&key_defined);

  if (iter_defined != createdActionsMap.end()) {
    printf("dp: defined action\n");

    return ((*iter_defined).second);
    
  } else {
        
    CActionKey key_default(user, action);
    ActionMap::iterator iter_default = createdActionsMap.find(&key_default);

    if ((iter_default != createdActionsMap.end())) {
      printf("dp: default action\n");
      // default action needs an object_type
      (*iter_default).second->SetObjectType(pList->get_object_type(objnm));
      
      return ((*iter_default).second);
    }
  }
  printf("dp: null action\n");
  // no action defined -> do nothing
  return GetNullAction();
}

CAction *CDispatcher::GetNullAction() const
{
  return nullacn;
}

AttrFlags CDispatcher::GetPrevErrFlags()
{
  /*  if (prevAction) {
    return prevAction->GetErrFlags();
    }*/

  return null_flags;  
}
