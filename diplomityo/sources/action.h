#ifndef CCONTROLDATA_H
#define CCONTROLDATA_H

#include "sem_template.h"
#include <string>
#include <sstream>
#include <list>
#include <map>
#include "obj.h"
#include "home.h"
#include "globals.h"
#include "memory_home.h"
#include "object_type_list.h"
#include "object_type.h"
#include "parser.h"
#include "value.h"
#include "attr.h"
#include "value_string.h"
#include "dataaccess.h"

class CDispatcher;
class CRequest;

typedef map<string, string> RequestMap;
typedef map<string,bool> AttrFlags;

using namespace std;

struct ViewData {
  ViewData() { object = 0; };
  obj *object;
  //  AttrFlags fltflags;
  AttrFlags fkflags;
  AttrFlags errflags;
  AttrFlags shwflags;
  AttrFlags hdnflags;
};

typedef list<ViewData> ViewDataList;

enum WebUser
{
    MONITOR=1,
    ADMIN,
    ENGINEER
};

class CAction {
  friend class CDataAccess;
 public:
  CAction(const string tpl, enum WebUser user = ADMIN, const object_type *pType = NULL, string hiddenAttributes = "", string shownAttributes = "", string additionalAttributes = "");
  virtual ~CAction();
  virtual ViewDataList &GetViewData();
  virtual const string GetViewTpl() const;
  virtual error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
  bool Execute(CRequest *req) const;
  virtual enum WebUser GetUser() const;
  virtual const string GetObjnm() const;
  virtual string GetActionType() const;
  //  virtual AttrFlags GetErrFlags();

  bool ParseHiddenAttributes();
  bool ParseShownAttributes();
  bool ParseVisibilityAttributes(const bool hidden);
  bool ParseAdditionalAttributes();

  void SetObjectType(const object_type *pType);
  void SetDispatcher(CDispatcher *dp);
  const object_type *GetObjectType() const;
  RequestMap GetRequest() const;
 protected:
  string m_objnm;
  const object_type *m_pType;
  string action_type;
  enum WebUser m_user;
  CParser m_parser;
  string m_tpl;
  CDispatcher *m_dp;
  AttrFlags null_flags;
  ViewDataList m_viewdata;

  // attribute settings
  string m_HiddenAttributes;
  string m_ShownAttributes;
  string m_AdditionalAttributes;
  AttrFlags m_HiddenAttributesMap;
  AttrFlags m_ShownAttributesMap;
  RequestMap m_AdditionalAttributesMap;

  context ctx;
  RequestMap m_request;
  bool validate_value(const string value, const string valtype) const;
  //  virtual error_t Cleanup();
  error_t SetAttrValues(ViewData &data, RequestMap *params, const bool strict, const bool onlyKeys, const bool feedback) const;
  bool SetAdditionalValues(RequestMap &req) const;
  bool GetHomePtrAndObjPtr(home*& pHome, obj*& pObj, CDispatcher *dp) const;
};

class COpenAddAction: public CAction {
 public:
  COpenAddAction(const string tpl, enum WebUser user, const object_type *pType = NULL, string hiddenAttributes = "", string additionalAttributes = "");
  ~COpenAddAction();
  error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
};

class CDisplaySingleAction: public CAction {
  CDataAccess daccess;
 public:
  CDisplaySingleAction(const string tpl, enum WebUser user, const object_type *pType = NULL, string additionalAttributes = "");
  virtual ~CDisplaySingleAction();
  virtual error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
};

class CDisplayListAction: public CAction {
  CDataAccess daccess;
 public:
  CDisplayListAction(const string tpl, enum WebUser user, const object_type *pType = NULL, string additionalAttributes = "");
  ~CDisplayListAction();
  error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
};

class CAddAction: public CAction {
 public:
  CAddAction(const string tpl, enum WebUser user, const object_type *pType = NULL, string shownAttributes = "", string additionalAttributes = "");
  ~CAddAction();
  error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
};

class COpenEditAction: public CAction {
  CDataAccess daccess;
 public:
  COpenEditAction(const string tpl, enum WebUser user, const object_type *pType = NULL, string hiddenAttributes = "", string additionalAttributes = "");
  ~COpenEditAction();
  error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
};

class CUpdateAction: public CAction {
 public:
  CUpdateAction(const string tpl, enum WebUser user, const object_type *pType = NULL, string additionalAttributes = "");
  ~CUpdateAction();
  error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
};

class CDeleteAction: public CAction {
 public:
  CDeleteAction(const string tpl, enum WebUser user, const object_type *pType = NULL, string additionalAttributes = "");
  ~CDeleteAction();
  error_t ExecuteAction(CRequest *req, CDispatcher *dp) const;
};

#endif

