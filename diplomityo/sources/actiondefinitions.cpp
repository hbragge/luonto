#include "actiondefinitions.h"

CActionKey::CActionKey(enum WebUser user, string action, string objnm): m_user(user), action_type(action), objnm(objnm) {}

CActionKey::~CActionKey() {}

enum WebUser CActionKey::GetUser() const
{
  return m_user;
}

const string CActionKey::GetObjnm() const
{
  return objnm;
}

string CActionKey::GetActionType() const
{
  return action_type;
}

int CActionKey::compare_with(const CActionKey &other) const
{
  int result = 0;

  if(m_user < other.GetUser())
    result = -1;
  if(m_user > other.GetUser())
    result = 1;

  if(action_type.compare(other.GetActionType()) < 0)
    result = -1;
  if(action_type.compare(other.GetActionType()) > 0)
    result = 1;
  
  if(objnm.compare(other.GetObjnm()) < 0)
    result = -1;
  if(objnm.compare(other.GetObjnm()) > 0)
    result = 1;

  return result;
}

CActionDefinitions::CActionDefinitions() {}

CActionDefinitions::~CActionDefinitions() {}

bool CActionDefinitions::CreateActions(ActionMap &createdActionsMap, object_type_list *pList, CDispatcher *dp)
{
  if( pList == NULL )
    {
      return false;
    }

  // definitions here - lasting NULL
  CAction *actions[MAX_NUMBER_OF_ACTIONS] =
    {
      // specified actions
      new COpenAddAction("/add.thtm", ADMIN, pList->get_object_type("bridge"), 
                         "mac priority forwarddelay garbcollinterv hello maxage stpstate", 
                         ""
                         ),
      new COpenEditAction("/edit.thtm", ADMIN, pList->get_object_type("bridge"), 
                         "mac priority forwarddelay garbcollinterv hello maxage stpstate", 
                         ""
                         ),
      new CAddAction("/show.thtm", ADMIN, pList->get_object_type("bridge"), 
                     "brname description ageingtime",
                     "$mac=macciosote"
                     /*"$ageingtime=30"
                     "$priority=32768"
                     "$forwarddelay=15"
                     "$garbcollinterv=4"
                     "$hello=2"
                     "$maxage=20"
                     "$stpstate=off"*/
                     ),
      new COpenAddAction("/add.thtm", ADMIN, pList->get_object_type("device_interface"), 
                         "card mac oper_rate oper_shdsl_annex oper_shdsl_pam status oper_fullduplex", 
                         ""
                         ),
      new CAddAction("/show.thtm", ADMIN, pList->get_object_type("device_interface"),
                     "type unit description rate_min rate_max shdsl_centralremote shdsl_annex_pam ethmodelink",
                     "$card=1"
                     "$description=if"
                     ),
      // default actions
      new COpenAddAction("/add.thtm", ADMIN),
      new CDisplaySingleAction("/show.thtm", ADMIN, NULL, ""),
      new CDisplayListAction("/list_cut.thtm", ADMIN),
      new CAddAction("/show.thtm", ADMIN),
      new COpenEditAction("/edit.thtm", ADMIN),
      new CUpdateAction("/show.thtm", ADMIN),
      new CDeleteAction("/index.thtm", ADMIN),
      // don't touch this NULL
      NULL
    };

  int i = 0;
  
  while(actions[i] != NULL && i < MAX_NUMBER_OF_ACTIONS)
    {
      const CActionKey *key = new CActionKey(actions[i]->GetUser(), actions[i]->GetActionType(), actions[i]->GetObjnm());
      actions[i]->SetDispatcher(dp);
      
      if(!actions[i]->ParseHiddenAttributes())
        return false;
      
      if(!actions[i]->ParseShownAttributes())
        return false;

      if(!actions[i]->ParseAdditionalAttributes())
        return false;

      createdActionsMap[key] = actions[i];
      
      i++;
    }
  
  return true;
}

