#ifndef CACTIONDEFINITIONS_H
#define CACTIONDEFINITIONS_H

#include "action.h"

#define MAX_NUMBER_OF_ACTIONS 200

class CActionKey {
  enum WebUser m_user;
  string action_type;
  string objnm;
 public:
  CActionKey(enum WebUser user, const string action, const string objnm = "");
  int compare_with(const CActionKey &other) const;
  enum WebUser GetUser() const;
  string GetActionType() const;
  const string GetObjnm() const;
  ~CActionKey();
};

struct keycmp
    {
        bool operator()(const CActionKey *key1, const CActionKey *key2) const {
//          return a->less_than(*b);
            return (key1->compare_with(*key2) == -1); // only compare key attributes here
        };
    };

typedef map<const CActionKey *, CAction *, keycmp> ActionMap;

class CActionDefinitions {
 public:
  CActionDefinitions();
  virtual ~CActionDefinitions();
  virtual bool CreateActions(ActionMap &createdActionsMap, object_type_list *pList, CDispatcher *dp);
};

#endif

